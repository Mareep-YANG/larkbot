package cn.mareep.larkbot.utils;

import cn.mareep.larkbot.Bot;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TencentCosUtil {
    private static COSClient cosClient;
    public TencentCosUtil() {
        COSCredentials cred = new BasicCOSCredentials(Bot.getConfig().get("tencentCloudSecretId"), Bot.getConfig().get("tencentCloudSecretKey"));
        Region region = new Region(Bot.getConfig().get("tencentCloudRegion"));
        ClientConfig clientConfig = new ClientConfig(region);
        cosClient = new COSClient(cred, clientConfig);
    }
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
