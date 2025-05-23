package cn.mareep.larkbot.entity.session;

import cn.mareep.larkbot.utils.MarkdownUtil;
import cn.mareep.larkbot.utils.TencentCosUtil;

/**
 * Markdown编辑会话，包含Markdown工具、COS工具和标题.
 */
public class MarkdownEditSession {
    /**
     * Markdown工具实例.
     */
    private final MarkdownUtil markdownUtil = new MarkdownUtil();
    /**
     * 腾讯云COS工具实例.
     */
    private final TencentCosUtil cosUtil = new TencentCosUtil();
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
     * 获取COS工具实例.
     *
     * @return TencentCosUtil实例
     */
    public TencentCosUtil getCosUtil() {
        return cosUtil;
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