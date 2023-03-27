package com.github.arena.challenges.weakmdparser;

class TextFormatting {

     static final String ITALIC_TEXT_REGEX = "_(.+)_";
     static final String BOLD_TEXT_REGEX = "__(.+)__";


    static String insertTextFormattingTags(String text) {
        return text
                .replaceAll(BOLD_TEXT_REGEX, "<strong>$1</strong>")
                .replaceAll(ITALIC_TEXT_REGEX, "<em>$1</em>");
    }
}
