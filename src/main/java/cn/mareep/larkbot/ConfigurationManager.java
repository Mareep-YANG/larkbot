package cn.mareep.larkbot;

import java.io.*;
import java.util.Properties;

/**
 * 配置管理器，支持从jar包外部目录读取和保存配置文件.
 */
public class ConfigurationManager {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + CONFIG_FILE_NAME;
    private final Properties properties = new Properties();

    public ConfigurationManager() {
        load();
    }

    /**
     * 加载配置文件.
     */
    public void load() {
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("未找到配置文件，将创建默认配置: " + CONFIG_PATH);
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存配置到文件.
     */
    public void save() {
        try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
            properties.store(output, "LarkBot 配置文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置项.
     */
    public String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * 设置配置项.
     */
    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * 获取配置项（带默认值）.
     */
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 判断配置项是否存在.
     */
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
} 