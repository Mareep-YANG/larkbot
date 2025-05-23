package cn.mareep.larkbot.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class MarkdownUtil {
    private final StringBuilder markdownBuilder = new StringBuilder();

    public void addParagraph(String text) {
        markdownBuilder.append(text).append("\n\n");
    }

    public void addImage(String altText, String url) {
        markdownBuilder.append("![").append(altText).append("](").append(url).append(")\n\n");
    }

    public void addHeader(String text, int level) {
        markdownBuilder.append("#".repeat(Math.max(0, level))).append(" ").append(text).append("\n\n");
    }


    public String toMarkdown() {
        return markdownBuilder.toString();
    }

    public String toHtml() {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Document document = parser.parse(markdownBuilder.toString());
        return renderer.render(document);
    }
}
