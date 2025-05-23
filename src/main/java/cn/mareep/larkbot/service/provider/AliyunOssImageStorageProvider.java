package cn.mareep.larkbot.service.provider;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.service.ImageStorageProvider;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AliyunOssImageStorageProvider implements ImageStorageProvider {
    private final OSS ossClient;
    public AliyunOssImageStorageProvider() {
        String endpoint = Bot.getConfig().get("AliyunOssEndpoint");
        String region = Bot.getConfig().get("AliyunOssRegion");
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(Bot.getConfig().get("AliyunAccessKeyID"),Bot.getConfig().get("AliyunAccessKeySecret"));
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region).build();
    }
    /**
     * 上传图片到阿里云oss
     *
     * @param fileName 文件名
     * @param data     图片字节数据
     */
    @Override
    public void uploadImage(String fileName, byte[] data) {
        String bucketName = Bot.getConfig().get("AliyunOssBucketName");
        String key = Bot.getConfig().get("AliyunImagePath") + fileName;
        File localFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            fos.write(data);
            ossClient.putObject(bucketName, key, localFile);
        } catch (IOException e) {
            Bot.getLogger().error("上传图片失败", e);
        } finally {
            localFile.delete();
        }

    }
}
