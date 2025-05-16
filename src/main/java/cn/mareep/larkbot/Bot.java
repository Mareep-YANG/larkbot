package cn.mareep.larkbot;


import cn.mareep.larkbot.listener.GroupMessageListener;
import cn.mareep.larkbot.listener.PrivateMessageListener;
import com.lark.oapi.ws.Client;

public class Bot {
    public static Client client;
    public static ConfigurationManager config = new ConfigurationManager();
    public static void main(String[] args) {
        new GroupMessageListener();
        new PrivateMessageListener();
        config.load();
        if (!config.containsKey("wordpressUsername")){
            config.set("wordpressUsername", "username");
        }
        if (!config.containsKey("wordpressPassword")){
            config.set("wordpressPassword", "password");
        }
        if (!config.containsKey("wordpressUrl")){
            config.set("wordpressUrl", "https://example.com");
        }
        config.save();
        client = new Client.Builder("cli_a88e1ef34ab9d00d","oyOtibq8lxb9jFUHmqAqchUzfEtTxqla")
                .eventHandler(LarkEventDispatcher.getDispatcher())
                .build();
        client.start();
    }
    public static ConfigurationManager getConfig() {
        return config;
    }
}