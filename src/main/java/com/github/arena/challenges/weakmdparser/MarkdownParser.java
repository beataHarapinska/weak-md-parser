package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public static final String NEW_LINE_SYMBOL = "\n";
    public static final String UL_OPENING_TAG = "<ul>";
    public static final String UL_CLOSING_TAG = "</ul>";

    private boolean activeList;

    MarkdownParser() {
        activeList = false;
    }

    public String parse(String markdown) {
        String[] lines = markdown.split(NEW_LINE_SYMBOL);
        StringBuilder result = new StringBuilder();

        for (int index = 0; index < lines.length; index++) {
            String parsedLine = LineParser.fromString(lines[index])
                    .insertTextFormattingTags()
                    .insertLineFormattingTags()
                    .toLine();
            boolean isLastLine = (index == lines.length - 1);
            addUnorderedListTagsToResult(result, parsedLine, isLastLine);
        }
        return result.toString();
    }

    void addUnorderedListTagsToResult(StringBuilder result, String theLine, boolean isLastIndex) {
        result.append(insertUlTagToLine(theLine));
        if (isLastIndex && activeList) result.append(UL_CLOSING_TAG);
    }

    private String insertUlTagToLine(String line) {
        if (isTheFirstListItem(line)) {
            activeList = true;
            line = UL_OPENING_TAG + line;
        } else if (isTheFirstLineNotInListScope(line)) {
            activeList = false;
            line = UL_CLOSING_TAG + line;
        }
        return line;
    }

    private boolean isTheFirstLineNotInListScope(String line) {
        return !line.matches("(<li>).*") && activeList;
    }

    private boolean isTheFirstListItem(String theLine) {
        return theLine.matches("(<li>).*") && !activeList;
    }
}