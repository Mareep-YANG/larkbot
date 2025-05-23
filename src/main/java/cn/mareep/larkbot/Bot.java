package cn.mareep.larkbot;


import cn.mareep.larkbot.event.LarkEventDispatcher;
import cn.mareep.larkbot.listener.GroupMessageListener;
import com.lark.oapi.ws.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LarkBot 主类，负责初始化配置、事件监听和启动Lark客户端.
 */
public class Bot {
    /**
     * Lark客户端实例.
     */
    public static Client client;
    /**
     * 配置管理器实例.
     */
    private static final ConfigurationManager config = new ConfigurationManager();
    /**
     * 日志记录器.
     */
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    /**
     * 程序主入口，初始化监听器、配置并启动Lark客户端.
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        new GroupMessageListener();
        initConfig();
        client = new Client.Builder(config.get("larkAppId"), config.get("larkAppSecret"))
                .eventHandler(LarkEventDispatcher.getDispatcher())
                .build();
        client.start();
    }

    /**
     * 获取配置管理器实例.
     *
     * @return 配置管理器
     */
    public static ConfigurationManager getConfig() {
        return config;
    }

    /**
     * 获取日志记录器.
     *
     * @return 日志记录器
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * 初始化配置项，若不存在则设置默认值.
     */
    private static void initConfig() {
        config.load();
        if (!config.containsKey("wordpressUsername")) {
            config.set("wordpressUsername", "username");
        }
        if (!config.containsKey("wordpressPassword")) {
            config.set("wordpressPassword", "password");
        }
        if (!config.containsKey("wordpressUrl")) {
            config.set("wordpressUrl", "https://example.com");
        }
        if (!config.containsKey("larkAppId")) {
            config.set("larkAppId", "larkAppId");
        }
        if (!config.containsKey("larkAppSecret")) {
            config.set("larkAppSecret", "larkAppSecret");
        }
        if (!config.containsKey("imageBaseUrl")) {
            config.set("imageBaseUrl", "imageBaseUrl");
        }
        if (!config.containsKey("imageStorageProvider")) {
            config.set("imageStorageProvider", "TencentCOS");
        }
        if (!config.containsKey("wordpressGroupId")) {
            config.set("wordpressGroupId", "wordpressGroupId");
        }
        config.save();
    }
}