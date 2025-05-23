# LarkBot

## 项目简介

LarkBot 是一个基于 Java 的飞书群聊机器人，支持 Markdown 编辑、图片上传、自动发布到 WordPress，并集成腾讯云 COS 作为图片存储。适用于需要在飞书群内协作编辑并自动发布内容到 WordPress 的场景。

## 主要功能

- **飞书群消息监听**：自动监听指定群聊消息，支持文本和图片。
- **Markdown 编辑模式**：群成员可通过指令进入 Markdown 编辑模式，分段输入内容，支持设置标题、插入图片。
- **一键发布 WordPress**：编辑完成后，自动将内容以 HTML 格式发布到指定 WordPress 站点。
- **图片自动上传**：群内发送的图片自动上传至腾讯云 COS，并在文章中引用。
- **配置灵活**：所有敏感信息和参数均通过 `config.properties` 配置文件管理。

## 快速开始

### 1. 克隆项目

```bash
git clone <your-repo-url>
cd larkbot
```
### 2. 修改配置

修改jar包同目录下的config.properties

### 3. 构建项目

本项目基于 Maven 构建，需 JDK 17+。

```bash
mvn clean package
```

### 4. 运行

```bash
java -cp target/larkbot-1.0-SNAPSHOT.jar cn.mareep.larkbot.Bot
```

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

详见 `pom.xml`。

## 目录结构

- `src/main/java/cn/mareep/larkbot/` 主要代码
- `src/main/resources/` 资源文件
- `config.properties` 配置文件

## 作者

- Mareep

## 许可证

MIT License 