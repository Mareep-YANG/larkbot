package cn.mareep.larkbot.sessions;

import cn.mareep.larkbot.utils.MarkdownUtil;

public class MarkdownEditSession {
    private final MarkdownUtil markdownUtil = new MarkdownUtil();
    private String title = "";

    public MarkdownUtil getMarkdownUtil() { return markdownUtil; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
} 