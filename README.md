# LarkBot

## 项目简介

LarkBot 是一个基于 Java 的飞书群聊机器人，支持 Markdown 编辑、图片上传、自动发布到 WordPress，并集成腾讯云 COS 作为图片存储。适用于需要在飞书群内协作编辑并自动发布内容到 WordPress 的场景。

未来可能会扩展功能基于飞书机器人构建私人生活助手，包括理财、日程、任务等场景
## 主要功能

- **飞书群消息监听**：自动监听指定群聊消息，支持文本和图片。
- **Markdown 编辑模式**：群成员可通过指令进入 Markdown 编辑模式，分段输入内容，支持设置标题、插入图片。
- **一键发布 WordPress**：编辑完成后，自动将内容以 HTML 格式发布到指定 WordPress 站点。
- **图片自动上传**：群内发送的图片自动上传至腾讯云 COS，并在文章中引用。
- **配置灵活**：所有敏感信息和参数均通过 `config.properties` 配置文件管理。

## 快速开始

### 1. 克隆项目

### 2. 构建项目

本项目基于 Maven 构建，需 JDK 17+。

```bash
mvn clean package
```


### 4. 修改配置
修改jar包同目录下的config.properties

#### 🛠 飞书应用配置
| 参数            | 说明                        | 示例值 |
|-----------------|---------------------------|-----|
| larkAppId       | 飞书开放平台应用的 App ID      | N/A |
| larkAppSecret   | 飞书开放平台应用的 App Secret  | N/A |

> 获取方式：在[飞书开放平台](https://open.feishu.cn/)创建应用后获取

#### 🖥 WordPress 配置
| 参数                | 说明                          | 示例值                   |
|--------------------|-----------------------------|-----------------------|
| wordpressUrl       | WordPress 站点地址            | https://www.mareep.cn |
| wordpressUsername  | 后台管理员账号                 | N/A                   |
| wordpressPassword  | 后台管理员密码                 | N/A                   |
| wordpressGroupId   | 绑定的飞书群组ID               | N/A                   |

> 注意：GroupId 格式为 `oc_` 开头的32位字符串，可通过飞书API获取

#### ☁️ 图床配置
| 参数                      | 说明                    | 示例值                            |
|--------------------------|-----------------------|--------------------------------|
| imageStorageProvider     | 图片存储服务商             | TencentCOS/AliyunOSS                    |
| imageBaseUrl             | 图片访问基础URL           | https://image.mareep.cn/image/ |
##### 腾讯云
| 参数                      | 说明                    | 示例值                            |
|--------------------------|-----------------------|--------------------------------|
| tencentCloudSecretId     | 腾讯云访问密钥 ID          | N/A                            |
| tencentCloudSecretKey    | 腾讯云访问密钥 Key         | N/A                            |
| tencentCloudBucketName   | COS 存储桶名称           | N/A                            |
| tencentCloudRegion       | 存储桶所属地域            | ap-nanjing                     |
| tencentCloudImagePath    | 图片存储路径              | image/                         |
##### 阿里云
| 参数                      | 说明                 | 示例值                            |
|--------------------------|--------------------|--------------------------------|
| AliyunAccessKeyID     | 阿里云访问密钥 ID         | N/A                            |
| AliyunAccessKeySecret    | 阿里云访问密钥 Key        | N/A                            |
| AliyunOssBucketName   | COS 存储桶名称          | N/A                            |
| AliyunOssRegion       | 存储桶所属地域            | ap-nanjing                     |
| AliyunOssEndpoint    | 你的OSS所属地域的endpoint |  https://oss-cn-hangzhou.aliyuncs.com                        |
| AliyunImagePath    | 图片存储路径(桶内)         | image/                         |
> 创建指引：
> 1. 登录[腾讯云控制台](https://console.cloud.tencent.com/)
> 2. 创建 COS 存储桶，确保地域与配置一致
> 3. 在[访问管理](https://console.cloud.tencent.com/cam/capi)创建API密钥

#### ♻️ 使用注意事项
1. 安全保密：请勿将 `SecretId`/`SecretKey` 和 `AppSecret` 提交至公开仓库
2. 路径匹配：`ImagePath` 需要以 `/` 结尾，并与 `imageBaseUrl` 路径层级保持一致
3. 权限检查：确保飞书应用已开通「消息与群组」等必要权限

### 3. 运行



## 群聊指令说明

- `/edit` 进入 Markdown 编辑模式
- `/title 标题` 设置文章标题
- 发送文本：添加为段落
- 发送图片：自动上传并插入
- `/done` 完成编辑并发布到 WordPress
- `/exit` 退出编辑模式

## 依赖

- [飞书开放平台 Java SDK (oapi-sdk)](https://github.com/larksuite/oapi-sdk-java)
- [flexmark-all](https://github.com/vsch/flexmark-java)（Markdown 转 HTML）
- [log4j2](https://logging.apache.org/log4j/2.x/)
- [腾讯云 COS Java SDK](https://github.com/tencentyun/cos-java-sdk-v5)
- ...

详见 `pom.xml`。

## 目录结构

- `src/main/java/cn/mareep/larkbot/` 主要代码
- `src/main/resources/` 资源文件
- `config.properties` 配置文件

## 作者

- Mareep

## 许可证

MIT License 