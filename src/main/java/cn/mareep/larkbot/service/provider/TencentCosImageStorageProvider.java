package cn.mareep.larkbot.service.provider;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.service.ImageStorageProvider;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 腾讯云COS图片存储Provider，实现图片上传功能。
 */
public class TencentCosImageStorageProvider implements ImageStorageProvider {
    private final COSClient cosClient;
    public TencentCosImageStorageProvider() {
        COSCredentials cred = new BasicCOSCredentials(Bot.getConfig().get("tencentCloudSecretId"), Bot.getConfig().get("tencentCloudSecretKey"));
        Region region = new Region(Bot.getConfig().get("tencentCloudRegion"));
        ClientConfig clientConfig = new ClientConfig(region);
        cosClient = new COSClient(cred, clientConfig);
    }
    /**
     * 上传图片到腾讯云COS.
     * @param fileName 文件名
     * @param data 图片字节数据
     */
    public void uploadImage(String fileName, byte[] data) {
        String bucketName = Bot.getConfig().get("tencentCloudBucketName");
        String key = Bot.getConfig().get("tencentCloudImagePath") + fileName;
        File localFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            fos.write(data);
            cosClient.putObject(bucketName, key, localFile);
        } catch (IOException e) {
            Bot.getLogger().error("上传图片失败", e);
        } finally {
            localFile.delete();
        }
    }
} 