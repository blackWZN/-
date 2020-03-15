package com.wzn.azblog.Statistics.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.net.InetSocketAddress;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRabbitAdmin(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        InetSocketAddress address = WebsocketUtil.getRemoteAddress(session);
        webSockets.add(this);
        System.out.println("有新的连接，总数" + webSockets.size());
        redisTemplate.opsForValue().set("onlineCount",webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        webSockets.remove(this);
        System.out.println("有新的断开，总数" + webSockets.size());
        redisTemplate.opsForValue().set("onlineCount",webSockets.size());
    }


}
