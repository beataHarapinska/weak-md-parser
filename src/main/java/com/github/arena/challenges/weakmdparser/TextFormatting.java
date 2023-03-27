package com.github.arena.challenges.weakmdparser;

class TextFormatting {

    static String applyTextFormatting(String text) {
        return parseToEmphasisedText(parseToImportantTest(text));
    }

    //This is not necessarily italic - to get italic one should use <i> tag
    private static String parseToEmphasisedText(String text) {
        return text.replaceAll("_(.+)_", "<em>$1</em>");
    }

    //This is not necessarily bold - to get bold one should use <b> tag
    private static String parseToImportantTest(String text) {
        return text.replaceAll("__(.+)__", "<strong>$1</strong>");
    }
}
