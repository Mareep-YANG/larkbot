package cn.mareep.larkbot.entity.session;

import cn.mareep.larkbot.service.ImageStorageProvider;
import cn.mareep.larkbot.service.ImageStorageProviderFactory;
import cn.mareep.larkbot.utils.MarkdownUtil;

/**
 * Markdown编辑会话，包含Markdown工具、COS工具和标题.
 */
public class MarkdownEditSession {
    /**
     * Markdown工具实例.
     */
    private final MarkdownUtil markdownUtil = new MarkdownUtil();
    /**
     * 图床提供商实例.
     */
    private final ImageStorageProvider imageProvider = ImageStorageProviderFactory.getProvider();
    /**
     * 当前编辑的标题.
     */
    private String title = "";

    /**
     * 获取Markdown工具实例.
     *
     * @return MarkdownUtil实例
     */
    public MarkdownUtil getMarkdownUtil() {
        return markdownUtil;
    }

    /**
     * 获取图床提供商实例.
     *
     * @return ImageStorageProvider实例
     */
    public ImageStorageProvider getImageProvider() {
        return imageProvider;
    }

    /**
     * 设置标题.
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取标题.
     *
     * @return 标题
     */
    public String getTitle() {
        return title;
    }
} 