package cn.mareep.larkbot.service;

import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.model.*;

import java.nio.charset.StandardCharsets;

/**
 * 消息服务类，封装了与Lark开放平台消息相关的操作.
 * 提供发送文本消息、获取消息图片等功能。
 */
public class MessageService implements IService {
    private final Client client;

    /**
     * 构造消息服务对象.
     *
     * @param appId     飞书应用AppId
     * @param appSecret 飞书应用AppSecret
     */
    public MessageService(String appId, String appSecret) {
        this.client = Client.newBuilder(appId, appSecret).build();
    }

    /**
     * 发送文本消息.
     *
     * @param receiveIdType 接收者ID类型（如 open_id, user_id, union_id, email）
     * @param receiveId     接收者ID
     * @param text          消息内容
     * @return 返回消息发送结果
     * @throws Exception 发送失败时抛出异常
     */
    public String sendTextMessage(String receiveIdType, String receiveId, String text) throws Exception {
        CreateMessageReq req = CreateMessageReq.newBuilder()
                .receiveIdType(receiveIdType)
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId(receiveId)
                        .msgType("text")
                        .content("{\"text\":\"" + text + "\"}")
                        .build())
                .build();

        CreateMessageResp resp = client.im().v1().message().create(req);

        if (!resp.success()) {
            throw new RuntimeException(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(),
                    Jsons.createGSON(true, false).toJson(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8))));
        }
        return Jsons.DEFAULT.toJson(resp.getData());
    }

    /**
     * 获取消息中的图片数据.
     *
     * @param messageID 消息ID
     * @param imageKey  图片Key
     * @return 图片的字节数组
     * @throws Exception 获取失败时抛出异常
     */
    public byte[] getMessageImage(String messageID, String imageKey) throws Exception {
        GetMessageResourceReq req = GetMessageResourceReq.newBuilder()
                .messageId(messageID)
                .fileKey(imageKey)
                .type("image")
                .build();

        GetMessageResourceResp resp = client.im().v1().messageResource().get(req);

        if (!resp.success()) {
            throw new RuntimeException(String.format("code:%s,msg:%s,reqId:%s, resp:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(),
                    Jsons.createGSON(true, false).toJson(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8))));
        }
        // 处理图片数据
        return resp.getData().toByteArray();
    }
} 