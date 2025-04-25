package com.rzfk.websocket;

import cn.hutool.core.util.StrUtil;
import com.rzfk.common.core.domain.model.LoginUser;
import com.rzfk.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * sid 为token
 */
@ServerEndpoint(value= "/ws/{sid}",configurator = ServerConfigurator.class)
@Component
@Slf4j
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static TokenService tokenService;
    @Autowired
    public void setApplicationContext(TokenService tokenService){
        WebSocketServer.tokenService = tokenService;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid 请求唯一标识
    private String sid = StrUtil.EMPTY;

    // socket登录持有的登录对象
    private LoginUser loginUser = null;


    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
    public String getSid() {
        return sid;
    }
    public LoginUser getLoginUser() {
        return loginUser;
    }
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        this.sid = sid;
        this.loginUser = tokenService.getLoginUser(sid);
        log.info("有新窗口开始监听sid:{},当前在线人数为{}",sid,getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为{}",getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口{}的信息:{}",sid,message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket发生错误");
        error.printStackTrace();
    }

    /**
     * 当前客户端发送消息
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 异步推送消息
     *
     * @param message
     */
    public static void sendWholeAsyncMessage(String message) {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("websocket异步发送消息异常{}",e);
                continue;

            }
        }
    }


    /**
     * 群发自定义消息，所有人员消息一致。
     * 人员消息不一致时应参照此方法在具体逻辑上参照构建
     */
    public static void sendInfo(@PathParam("sid") String sid, String message) throws IOException {
        log.error("推送消息到窗口sid{}，推送内容:{}",sid,message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                log.error("websocket发送消息异常{}",e);
                continue;
            }
        }
    }

    private int getOnlineCount() {
        return onlineCount.get();
    }

    private void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    private void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
