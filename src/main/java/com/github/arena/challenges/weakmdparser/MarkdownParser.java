package com.github.arena.challenges.weakmdparser;

import static com.github.arena.challenges.weakmdparser.TextFormatting.*;

public class MarkdownParser {

    private boolean activeList;

    MarkdownParser() {
        activeList = false;
    }

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            line = applyTextFormatting(line);

            if (isHeading(line)) {
                line = parseToHeading(line);
            } else if (isListItem(line)) {
                line = parseToListItem(line);
            } else {
                line = parseToParagraph(line);
            }
            addUlTagToCreateList(result, line);

            result.append(line);
        }

        if (activeList) {
            result.append("</ul>");
        }
        return result.toString();
    }

    private boolean isListItem(String line) {
        return line.startsWith("*");
    }

    private boolean isHeading(String line) {
        return line.startsWith("#");
    }

    private void addUlTagToCreateList(StringBuilder result, String line) {
        if (isTheFirstListItem(line)) {
            activeList = true;
            result.append("<ul>");
        } else if (isTheFirstLineNotInListScope(line)) {
            activeList = false;
            result.append("</ul>");
        }
    }

    private boolean isTheFirstLineNotInListScope(String line) {
        return !line.matches("(<li>).*") && activeList;
    }

    private boolean isTheFirstListItem(String theLine) {
        return theLine.matches("(<li>).*")  && !activeList;
    }

    private String parseToHeading(String line) {
        return wrapTextWithTag(line.substring(getNumberOfHashes(line) + 1), "h" + getNumberOfHashes(line));
    }

    private int getNumberOfHashes(String line) {
        int index = 0;
        while (line.charAt(index) == '#') {
            index++;
        }
        return index;
    }

    private String parseToListItem(String line) {
        return wrapTextWithTag(line.substring(2), "li");
    }

    private String parseToParagraph(String line) {
        return wrapTextWithTag(line, "p");
    }

    private String wrapTextWithTag(String text, String tag) {
        return String.format("<%s>%s</%s>", tag, text, tag);
    }
}