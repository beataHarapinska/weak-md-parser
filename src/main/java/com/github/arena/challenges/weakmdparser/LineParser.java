package com.github.arena.challenges.weakmdparser;

class LineParser {

    static final String HEADING_MARKDOWN = "#";
    static final String HEADING_TAG_LETTER = "h";
    static final String LIST_ITEM_MARKDOWN = "*";
    static final String LIST_ITEM_TAG_SYMBOL = "li";
    static final String PARAGRAPH_TAG_SYMBOL = "p";

    static String parseLineSpecificTags(String line) {
        if (isHeading(line)) {
            line = insertHeadingTag(line);
        } else if (isListItem(line)) {
            line = insertListItemTag(line);
        } else {
            line = insertParagraphTag(line);
        }
        return line;
    }

    private static boolean isHeading(String line) {
        return line.startsWith(HEADING_MARKDOWN);
    }

    private static String insertHeadingTag(String line) {
        String lineWithoutHeadingMarkdown = line.substring(getLevelOfHeading(line) + 1);
        String headingTagSymbol = HEADING_TAG_LETTER + getLevelOfHeading(line);
        return wrapTextWithTag(lineWithoutHeadingMarkdown, headingTagSymbol);
    }

    private static boolean isListItem(String line) {
        return line.startsWith(LIST_ITEM_MARKDOWN);
    }

    private static String insertListItemTag(String line) {
        String lineWithoutLiMarkdown = line.substring(2);
        return wrapTextWithTag(lineWithoutLiMarkdown, LIST_ITEM_TAG_SYMBOL);
    }

    private static String insertParagraphTag(String line) {
        return wrapTextWithTag(line, PARAGRAPH_TAG_SYMBOL);
    }

    private static String wrapTextWithTag(String text, String tagSymbol) {
        return String.format("<%s>%s</%s>", tagSymbol, text, tagSymbol);
    }

    private static int getLevelOfHeading(String line) {
        int index = 0;
        while (line.charAt(index) == '#') {
            index++;
        }
        return index;
    }
}
