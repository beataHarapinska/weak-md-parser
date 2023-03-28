package com.github.arena.challenges.weakmdparser;

class TextFormatParser {

    static final String MARKDOWN_BOLD_TEXT = "__(.+)__";
    static final String HTML_BOLD_TEXT = "<strong>$1</strong>";
    static final String MARKDOWN_ITALIC_TEXT = "_(.+)_";
    static final String HTML_ITALIC_TEXT = "<em>$1</em>";

    static String parseTextFormattingTags(String text) {
        return text
                .replaceAll(MARKDOWN_BOLD_TEXT, HTML_BOLD_TEXT)
                .replaceAll(MARKDOWN_ITALIC_TEXT, HTML_ITALIC_TEXT);
    }
}
