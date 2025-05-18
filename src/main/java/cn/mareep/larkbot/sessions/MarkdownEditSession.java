package cn.mareep.larkbot.sessions;

import cn.mareep.larkbot.utils.MarkdownUtil;
import cn.mareep.larkbot.utils.TencentCosUtil;

public class MarkdownEditSession {
    private final MarkdownUtil markdownUtil = new MarkdownUtil();
    private final TencentCosUtil cosUtil = new TencentCosUtil();
    private String title = "";

    public MarkdownUtil getMarkdownUtil() { return markdownUtil; }
    public TencentCosUtil getCosUtil() { return cosUtil; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
} 