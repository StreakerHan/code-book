package com.rzfk.websocket;


import com.rzfk.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 *
 * 正式msg应为有意义的json数据,至少包含 type:
 */
/**
 * socket公共方法调用
 * msg应为有意义的json数据便于区分,至少包含 type: 数据类型 data: 数据内容
 * @author jpx
 * @date 2022/3/3 10:08
 **/

@Slf4j
@Service("com.rzfk.websocket.WebSocketService")
public class WebSocketService {
    @Autowired
    private  TokenService tokenService;


    /**
     * 发送消息
     * @param token 为null群发，登录用户可用SecurityUtils.getLoginUser().getToken()获取，其它指定用户不建议使用此方法
     * @param msg
     */
    public void sendMsg(String token,String msg) {
        try {
            WebSocketServer.sendInfo(token,msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定用户发送消息
     * @param username 登录用户方便获取token的不建议使用
     * @param msg
     */
    public void sendMsgToUser(String username,String msg){
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = WebSocketServer.getWebSocketSet();
        for (WebSocketServer item : webSocketSet) {
            try{
                if(username.equals(item.getLoginUser().getUsername())){
                    item.sendMessage(msg);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
