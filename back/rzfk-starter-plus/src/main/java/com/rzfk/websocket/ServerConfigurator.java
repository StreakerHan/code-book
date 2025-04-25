package com.rzfk.websocket;

/**
 * @author jpx
 * @date 2022/3/2 17:38
 **/

import cn.hutool.core.util.StrUtil;
import com.rzfk.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @ClassName ServerConfigurator
 * @Description: server 配置，进行行为修改，鉴权等
 * @Author
 * @Date 2020/1/4 11:11
 * @Version V1.0
 **/
@Slf4j
@Component
public class ServerConfigurator  extends ServerEndpointConfig.Configurator {

    private static TokenService tokenService;
    @Autowired
    public void setApplicationContext(TokenService tokenService){
        ServerConfigurator.tokenService = tokenService;
    }

    /**
     * token鉴权认证
     * @param originHeaderValue
     * @return
     */
    @Override
    public boolean checkOrigin(String originHeaderValue) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String requestUri = servletRequestAttributes.getRequest().getRequestURI();
        String token = null;
        if (StrUtil.isNotEmpty(requestUri) && (requestUri.contains("/ws"))) {
            String[] splits = requestUri.split("/");
            token = splits[splits.length - 1];
        }
        log.info("token：{}",token);
        try{
            String username = tokenService.getUsernameFromToken(token);
            return super.checkOrigin(originHeaderValue);
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Modify the WebSocket handshake response
     * 修改websocket 返回值
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
    }


}
