package ru.kosinov;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kosinov.bean.ChatObject;
import ru.kosinov.bean.Player;
import ru.kosinov.bean.Role;

import java.util.*;
import java.util.stream.Collectors;

import static ru.kosinov.bean.Role.WOLF;

/**
 * Created by Kosinov_KV
 * on 22.10.2015. netty-socketio-spring.
 */
@Component
public class ChatEventHandler {

    private final SocketIOServer server;

    private Map<UUID,Player> playerMap = new HashMap<>();

    @Autowired
    public ChatEventHandler(SocketIOServer server) {
        this.server = server;
    }

    @OnEvent(value = "chatevent")
    public void onEvent(SocketIOClient client, AckRequest request, ChatObject data) {
        UUID uuid = client.getSessionId();
        Player player = playerMap.get(uuid);

        data.setUserName(player.getName());
        if (player.isBanned())
            server.getClient(uuid).sendEvent("chatevent",new ChatObject("sys","你已被禁言"));
        else
            server.getBroadcastOperations().sendEvent("chatevent", data);
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        UUID uuid = client.getSessionId();
        Player player = new Player(uuid);
        playerMap.put(uuid,player);

        server.getBroadcastOperations().sendEvent("chatevent",
            new ChatObject("sys",player.getName()+"加入了聊天室"));
        server.getClient(uuid).sendEvent("chatevent",
            new ChatObject("sys",player.getName()+",你的身份是"+player.getRole().getName()));

        List<Player> wolfUsers = getRoleUsers(WOLF);
        if(player.getRole()==WOLF)
            wolfUsers.forEach(wolf->{
                SocketIOClient c = server.getClient(wolf.getUuid());
                if(c!=null)// 用户存在
                    c.sendEvent("chatevent",new ChatObject("sys",
                        wolf!=player?player.getName()+"是你的狼队友!":"狼阵营:"+getUserNames(wolfUsers)));
                else{
                    playerMap.remove(wolf.getUuid());
                }
            });

    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        UUID uuid = client.getSessionId();
        Player player = playerMap.remove(uuid);
        server.getBroadcastOperations().sendEvent("chatevent",
            new ChatObject("sys",player.getName()+
                "(身份:"+player.getRole().getName()+")离开了聊天室"));
    }


    private List<Player> getRoleUsers(Role role){
        List<Player> roleUsers = new ArrayList();
        playerMap.forEach((k,w)->{if(role.equals(w.getRole()))roleUsers.add(w);});
        return roleUsers;
    }

    private List<String> getUserNames(List<Player> users){
        return users.stream()
            .map(Player::getName).collect(Collectors.toList());
    }

}
