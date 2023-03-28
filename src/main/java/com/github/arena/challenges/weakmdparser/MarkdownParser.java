package com.github.arena.challenges.weakmdparser;

import java.util.ArrayList;
import java.util.List;

import static com.github.arena.challenges.weakmdparser.LineParser.*;
import static com.github.arena.challenges.weakmdparser.ListParser.*;
import static com.github.arena.challenges.weakmdparser.TextFormatParser.*;

public class MarkdownParser {

    static final String NEW_LINE_SYMBOL = "\n";

    public static String parse(String markdown) {
        String[] lines = markdown.split(NEW_LINE_SYMBOL);
        int numberOfLines = lines.length;
        List<String> result = new ArrayList<>();

        for (String theLine : lines) {
            theLine = parseTextFormattingTags(theLine);
            theLine = parseLineSpecificTags(theLine);
            theLine = wrapListsWithUlTags(result, theLine, numberOfLines);
            result.add(theLine);
        }
        return String.join("", result);
    }
}