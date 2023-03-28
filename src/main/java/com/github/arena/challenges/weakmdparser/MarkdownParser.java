package com.github.arena.challenges.weakmdparser;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {

    public static final String NEW_LINE_SYMBOL = "\n";
    public static final String UL_OPENING_TAG = "<ul>";
    public static final String UL_CLOSING_TAG = "</ul>";

    public static String parse(String markdown) {
        String[] lines = markdown.split(NEW_LINE_SYMBOL);
        int numberOfLines = lines.length;
        List<String> result = new ArrayList<>();

        for (String line : lines) {
            String theLine = LineParser.fromString(line)
                    .insertTextFormattingTags()
                    .insertLineFormattingTags()
                    .toLine();
            theLine = addUnorderedListTags(numberOfLines, result, theLine);
            result.add(theLine);
        }
        return String.join("", result);
    }

    private static String addUnorderedListTags(int numberOfLines, List<String> result, String theLine) {
        if (shouldAddUlOpeningTagToTheLine(result, theLine)) {
            theLine = UL_OPENING_TAG + theLine;
        }
        if (shouldAddUlClosingTagToTheLine(result, theLine)) {
            theLine = UL_CLOSING_TAG + theLine;
        }
        if (shouldAddClosingTagAtTheEnd(result, theLine, numberOfLines)) {
            theLine += UL_CLOSING_TAG;
        }
        return theLine;
    }

    private static boolean shouldAddClosingTagAtTheEnd(List<String> result, String theLine, int lastIndex) {
        return result.isEmpty()
               ? isListItem(theLine)
               : (result.size() == lastIndex - 1) && isListItem(theLine) && isPreviousLineListItem(result);
    }

    private static boolean shouldAddUlClosingTagToTheLine(List<String> result, String theLine) {
        return !result.isEmpty() && !isListItem(theLine) && isPreviousLineListItem(result);
    }

    private static boolean shouldAddUlOpeningTagToTheLine(List<String> result, String theLine) {
        return result.isEmpty()
               ? isListItem(theLine)
               : isListItem(theLine) && !isPreviousLineListItem(result);
    }

    private static boolean isPreviousLineListItem(List<String> result) {
        return result.get(result.size() - 1).matches(".*(</li>)");
    }

    private static boolean isListItem(String line) {
        return line.matches("(<li>).*");
    }
}