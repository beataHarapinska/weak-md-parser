package com.github.arena.challenges.weakmdparser;

import java.util.List;

class ListParser {

    static final String UL_OPENING_TAG = "<ul>";
    static final String UL_CLOSING_TAG = "</ul>";
    static final String HTML_LI_CLOSING_TAG_MARKER = ".*(</li>)";
    static final String HTML_LI_OPENING_TAG_MARKER = "(<li>).*";

    static String wrapListsWithUlTags(List<String> result, String theLine, int numberOfLines) {
        if (shouldAddUlOpeningTagToTheLine(result, theLine)) {
            theLine = UL_OPENING_TAG + theLine;
        }
        if (shouldAddUlClosingTagToTheLine(result, theLine)) {
            theLine = UL_CLOSING_TAG + theLine;
        }
        if (shouldAddUlClosingTagAtTheEnd(result, theLine, numberOfLines)) {
            theLine += UL_CLOSING_TAG;
        }
        return theLine;
    }

    private static boolean shouldAddUlOpeningTagToTheLine(List<String> result, String theLine) {
        return result.isEmpty()
               ? isThisLineListItem(theLine)
               : isThisLineListItem(theLine) && !isPreviousLineListItem(result);
    }

    private static boolean shouldAddUlClosingTagToTheLine(List<String> result, String theLine) {
        return !result.isEmpty() && !isThisLineListItem(theLine) && isPreviousLineListItem(result);
    }

    private static boolean shouldAddUlClosingTagAtTheEnd(List<String> result, String theLine, int lastIndex) {
        return result.isEmpty()
               ? isThisLineListItem(theLine)
               : (result.size() == lastIndex - 1) && isThisLineListItem(theLine) && isPreviousLineListItem(result);
    }

    private static boolean isPreviousLineListItem(List<String> result) {
        return result.get(result.size() - 1).matches(HTML_LI_CLOSING_TAG_MARKER);
    }

    private static boolean isThisLineListItem(String line) {
        return line.matches(HTML_LI_OPENING_TAG_MARKER);
    }
}
