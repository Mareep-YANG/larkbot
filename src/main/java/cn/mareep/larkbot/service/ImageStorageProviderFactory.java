package cn.mareep.larkbot.service;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.service.provider.AliyunOssImageStorageProvider;
import cn.mareep.larkbot.service.provider.TencentCosImageStorageProvider;


public class ImageStorageProviderFactory {
    private static ImageStorageProvider provider;

    public static ImageStorageProvider getProvider() {
        if (provider == null) {
            String type = Bot.getConfig().get("imageStorageProvider");
            if ("TencentCOS".equalsIgnoreCase(type)) {
                provider = new TencentCosImageStorageProvider();
            } else if ("AliyunOSS".equalsIgnoreCase(type)) {
                provider = new AliyunOssImageStorageProvider();
            } else {
                Bot.getLogger().error("不支持的图片存储类型{}", type);
                throw new IllegalArgumentException("不支持的图片存储类型: " + type);
            }
        }
        return provider;
    }
} 