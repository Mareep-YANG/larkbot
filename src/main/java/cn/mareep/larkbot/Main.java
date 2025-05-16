package cn.mareep.larkbot;


import cn.mareep.larkbot.listener.GroupMessageListener;
import cn.mareep.larkbot.listener.PrivateMessageListener;
import com.lark.oapi.service.im.v1.model.*;
import com.lark.oapi.ws.Client;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.im.ImService;
import cn.mareep.larkbot.event.*;

public class Main {
    public static Client client;
    public static void main(String[] args) {
        new GroupMessageListener();
        new PrivateMessageListener();
        client = new Client.Builder("cli_a88e1ef34ab9d00d","oyOtibq8lxb9jFUHmqAqchUzfEtTxqla")
                .eventHandler(LarkEventDispatcher.getDispatcher())
                .build();
        client.start();
    }
}