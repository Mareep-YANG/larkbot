package cn.mareep.larkbot.service;

public interface ImageStorageProvider extends IService {
    /**
     * 上传图片
     * @param fileName 文件名
     * @param data 图片字节数据
     */
    void uploadImage(String fileName, byte[] data);
} 