package cn.mareep.larkbot.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * Markdown工具类，支持Markdown文本的构建与转换.
 */
public class MarkdownUtil {
    private final StringBuilder markdownBuilder = new StringBuilder();

    /**
     * 添加段落文本.
     *
     * @param text 段落内容
     */
    public void addParagraph(String text) {
        markdownBuilder.append(text).append("\n\n");
    }

    /**
     * 添加图片.
     *
     * @param altText 图片的替代文本
     * @param url     图片URL
     */
    public void addImage(String altText, String url) {
        markdownBuilder.append("![").append(altText).append("](").append(url).append(")\n\n");
    }

    /**
     * 添加标题.
     *
     * @param text  标题内容
     * @param level 标题级别（1-6）
     */
    public void addHeader(String text, int level) {
        markdownBuilder.append("#".repeat(Math.max(0, level))).append(" ").append(text).append("\n\n");
    }

    /**
     * 获取完整的Markdown文本.
     *
     * @return Markdown字符串
     */
    public String toMarkdown() {
        return markdownBuilder.toString();
    }

    /**
     * 将Markdown文本转换为HTML.
     *
     * @return HTML字符串
     */
    public String toHtml() {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Document document = parser.parse(markdownBuilder.toString());
        return renderer.render(document);
    }
}
