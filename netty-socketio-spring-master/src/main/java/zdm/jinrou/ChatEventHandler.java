package zdm.jinrou;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zdm.jinrou.bean.ChatObject;
import zdm.jinrou.bean.PInfo;
import zdm.jinrou.bean.Player;
import zdm.jinrou.bean.Role;
import zdm.jinrou.service.PlayerInfoService;
import zdm.jinrou.util.IMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kosinov_KV
 * on 22.10.2015. netty-socketio-spring.
 */
@Component
public class ChatEventHandler {

    private final SocketIOServer server;

    private Map<UUID,Player> playerMap;

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    public ChatEventHandler(SocketIOServer server) {
        this.server = server;
        playerMap = Maps.newHashMap();
    }

    @OnEvent(value = "chatevent")
    public void onEvent(SocketIOClient client, AckRequest request, ChatObject data) {
        UUID uuid = client.getSessionId();
        String message = data.getMessage();
        if(Strings.isBlank(data.getName())) {// 无TOKEN,判断是否登录
            if(Strings.isNotBlank(message)){
                String[] msplit = message.split(" ");
                if(msplit.length == 3 && "login".equals(msplit[0])) {// 获取账号密码
                    IMap login = playerInfoService.login(msplit[1], msplit[2]);
                    if(login.isSuc()) {
                        server.getClient(uuid).sendEvent("chatevent",
                                new ChatObject("sys", "登录成功"));
                        server.getClient(uuid).sendEvent("chatevent",
                                new ChatObject("token", (String)login.getData()));
                    }
                    else
                        server.getClient(uuid).sendEvent("chatevent",
                                new ChatObject("sys", "登录失败:"+login.getMsg()));
                }
            }
            server.getClient(uuid).sendEvent("chatevent", new ChatObject("sys", "请先登录"));
            return;
        }
        PInfo pInfo = playerInfoService.getPInfoByToken(data.getName());
        Player player = playerMap.get(uuid);
        if(pInfo==null){
            server.getClient(uuid).sendEvent("chatevent", new ChatObject("sys", "该玩家不存在"));
            return;
        }else if(!pInfo.getId().equals(player.getpInfoId())){
            server.getClient(uuid).sendEvent("chatevent", new ChatObject("sys", "玩家信息不匹配"));
            return;
        }

        data.setName(pInfo.getpName());
        if (player.isBanned())
            server.getClient(uuid).sendEvent("chatevent",new ChatObject("sys","你已被禁言"));
        else
            server.getBroadcastOperations().sendEvent("chatevent", data);
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        UUID uuid = client.getSessionId();
        Player player = new Player(uuid,-1L);
        playerMap.put(uuid,player);

        server.getBroadcastOperations().sendEvent("chatevent",
            new ChatObject("sys",player.getName()+"加入了聊天室"));
        server.getClient(uuid).sendEvent("chatevent",
            new ChatObject("sys",player.getName()+",你的身份是"+player.getRole().getName()));

        List<Player> wolfUsers = getRoleUsers(Role.WOLF);
        if(player.getRole()== Role.WOLF)
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
