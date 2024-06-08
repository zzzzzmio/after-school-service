package com.mio.afterschoolservice.Websocket;

import cn.hutool.json.JSONUtil;
import com.mio.afterschoolservice.pojo.Imsingle;
import com.mio.afterschoolservice.service.ImsingleService;
import com.mio.afterschoolservice.utils.MyApplicationContextUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/imserver")
@Component
public class WebsocketServer {
    private static final Map<String, Session> sessionMap =new ConcurrentHashMap<>();
    private ImsingleService imsingleService= MyApplicationContextUtil.getBean(ImsingleService.class);

    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session){
        sessionMap.put(session.getId(),session);
    }

    //连接关闭调用的方法
    @OnClose
    public void onClose(Session session){
        sessionMap.remove(session.getId());
    }

    //消息中转站
    @OnMessage
    public void onMessage(String message, Session session){
//        System.out.println(message);
        Imsingle imsingle= JSONUtil.toBean(message, Imsingle.class);
//        System.out.println(imsingle);
        imsingleService.add(imsingle);//后期需改为add方法
        String jsonStr = JSONUtil.toJsonStr(imsingle);//处理后的消息体
        this.sendAllMessage(jsonStr);
    }

    @OnError
    public void onError(Session session,Throwable e){
        System.out.println(e);
    }

    private void sendAllMessage(String message){
        try {
            for (Session session:sessionMap.values()){
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
