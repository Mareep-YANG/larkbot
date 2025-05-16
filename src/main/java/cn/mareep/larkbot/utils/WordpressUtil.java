package cn.mareep.larkbot.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import com.google.gson.JsonObject;

public class WordpressUtil {
    /**
     * 发布WordPress文章
     * @param url WordPress站点地址（如：https://www.yourWebsite.cn）
     * @param username 用户名
     * @param password 应用密码
     * @param title 标题
     * @param content 正文内容
     * @param categories 分类ID（可选，传null则不设置）
     * @return 发布结果字符串
     */
    public static String publishPost(String url, String username, String password, String title, String content, Integer categories) {
        try {
            String credentials = username + ":" + password;
            String token = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + token;

            String postUrl = url + "/wp-json/wp/v2/shuoshuo";

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("title", title);
            jsonObject.addProperty("status", "publish");
            jsonObject.addProperty("content", content);
            jsonObject.addProperty("post_type", "shuoshuo");
            if (categories != null) {
                jsonObject.addProperty("categories", categories);
            }
            String json = jsonObject.toString();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(postUrl))
                    .header("Authorization", authHeader)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                return "文章发布成功！";
            } else {
                return "发布失败，状态码：" + response.statusCode() + ", 响应内容：" + response.body();
            }
        } catch (Exception e) {
            return "发布异常：" + e.getMessage();
        }
    }
}
