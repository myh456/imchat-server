package com.imchatserver.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imchatserver.entity.Messages;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{uno}")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
                System.out.println("消息发送成功");
            }
        } else {
            System.out.println("对方未在线");
        }
    }
    //给指定用户发送信息
    public void unicast(String cno, String message){
        Session session = sessionPools.get(cno);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 群发消息
    public void broadcast(String message){
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uno") String uno){
        sessionPools.put(uno, session);
        addOnlineCount();
        System.out.println("===>工号：" + uno + "，加入webSocket连接，当前人数为" + onlineNum);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "uno") String uno){
        sessionPools.remove(uno);
        subOnlineCount();
        System.out.println("===>工号：" + uno + "，断开webSocket连接，当前人数为" + onlineNum);
    }

    //收到客户端信息后，根据接收人的uno把消息推下去或者群发
    @OnMessage
    public void onMessage(String message, @PathParam(value = "uno") String uno) {
        Messages msg=JSON.parseObject(message, Messages.class);
        if (Objects.equals(msg.getCno(), "0")) {
            broadcast(msg.getText());
        } else {
            unicast(msg.getCno(), msg.getText());
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("WebSocket发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    public static List<String> getOnlineUsers() {
        return new ArrayList<>(sessionPools.keySet());
    }
}