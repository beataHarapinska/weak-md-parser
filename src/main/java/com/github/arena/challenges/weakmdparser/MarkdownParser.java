package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();
        boolean activeList = false;

        for (String line : lines) {

            String theLine = parseHeading(line);

            if (theLine == null) {
                theLine = parseToListItem(line);
            }

            if (theLine == null) {
                theLine = parseToParagraph(line);
            }

            if (isTheFirstListItem(theLine, activeList)) {
                activeList = true;
                result.append("<ul>");
            } else if (isNotTheListItem(theLine, activeList)) {
                activeList = false;
                result.append("</ul>");
            }
            result.append(theLine);
        }

        if (activeList) {
            result.append("</ul>");
        }

        return result.toString();
    }

    private boolean isNotTheListItem(String theLine, boolean activeList) {
        return !theLine.matches("(<li>).*") && activeList;
    }

    private boolean isTheFirstListItem(String theLine, boolean activeList) {
        return theLine.matches("(<li>).*") && !theLine.matches("(<h).*") && !theLine.matches("(<p>).*") && !activeList;
    }

    private String parseHeading(String markdown) {
        int count = 0;
        for (int i = 0; markdown.charAt(i) == '#'; i++) {
            count++;
        }
        return count == 0 ? null : wrapTextWithTag(markdown.substring(count + 1), "h" + count);
    }

    private String parseToListItem(String markdown) {
        if (markdown.startsWith("*")) {
            String skipAsterisk = markdown.substring(2);
            String listItemString = parseToBoldAndItalic(skipAsterisk);
            return wrapTextWithTag(listItemString,  "li");
        }
        return null;
    }

    private String parseToParagraph(String markdown) {
        return wrapTextWithTag(parseToBoldAndItalic(markdown), "p");
    }

    private String parseToBoldAndItalic(String markdown) {
        return parseToItalic(parseToBold(markdown));
    }

    private String parseToItalic(String workingOn) {
        return workingOn.replaceAll("_(.+)_", "<em>$1</em>");
    }

    private String parseToBold(String markdown) {
        return markdown.replaceAll("__(.+)__", "<strong>$1</strong>");
    }

    private String wrapTextWithTag(String text, String tag){
        return String.format("<%s>%s</%s>", tag, text, tag);
    }
}