package cn.mareep.larkbot;


import cn.mareep.larkbot.listener.GroupMessageListener;
import cn.mareep.larkbot.listener.PrivateMessageListener;
import com.lark.oapi.ws.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bot {
    public static Client client;
    private static final ConfigurationManager config = new ConfigurationManager();
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    public static void main(String[] args) {
        new GroupMessageListener();
        new PrivateMessageListener();
        initConfig();
        client = new Client.Builder(config.get("larkAppId"),config.get("larkAppSecret"))
                .eventHandler(LarkEventDispatcher.getDispatcher())
                .build();
        client.start();
    }
    public static ConfigurationManager getConfig() {
        return config;
    }
    public static Logger getLogger() {
        return logger;
    }
    private static void initConfig(){
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
        if (!config.containsKey("larkAppId")){
            config.set("larkAppId", "larkAppId");
        }
        if (!config.containsKey("larkAppSecret")){
            config.set("larkAppSecret", "larkAppSecret");
        }
        if (!config.containsKey("tencentCloudSecretId")){
            config.set("tencentCloudSecretId", "tencentCloudSecretId");
        }
        if (!config.containsKey("tencentCloudSecretKey")){
            config.set("tencentCloudSecretKey", "tencentCloudSecretKey");
        }
        if (!config.containsKey("tencentCloudRegion")){
            config.set("tencentCloudRegion", "tencentCloudRegion");
        }
        if (!config.containsKey("tencentCloudBucketName")){
            config.set("tencentCloudBucketName", "tencentCloudBucketName");
        }
        if (!config.containsKey("tencentCloudImagePath")){
            config.set("tencentCloudImagePath", "tencentCloudImagePath");
        }
        if (!config.containsKey("imageBaseUrl")){
            config.set("imageBaseUrl", "imageBaseUrl");
        }
        if (!config.containsKey("imageType")){
            config.set("imageType", "TencentCOS");
        }
        config.save();
    }
}