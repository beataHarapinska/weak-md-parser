package com.github.arena.challenges.weakmdparser;

public class LineFormatting {

    static String insertLineFormattingTags(String line) {
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
        return line.startsWith("#");
    }

    private static String insertHeadingTag(String line) {
        return wrapTextWithTag(line.substring(getNumberOfHashes(line) + 1), "h" + getNumberOfHashes(line));
    }

    private static boolean isListItem(String line) {
        return line.startsWith("*");
    }

    private static String insertListItemTag(String line) {
        return wrapTextWithTag(line.substring(2), "li");
    }

    private static String insertParagraphTag(String line) {
        return wrapTextWithTag(line, "p");
    }

    private static String wrapTextWithTag(String text, String tagSymbol) {
        return String.format("<%s>%s</%s>", tagSymbol, text, tagSymbol);
    }

    private static int getNumberOfHashes(String line) {
        int index = 0;
        while (line.charAt(index) == '#') {
            index++;
        }
        return index;
    }
}
