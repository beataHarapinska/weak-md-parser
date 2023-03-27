package com.github.arena.challenges.weakmdparser;

public class LineParser {

    private String line;

    private LineParser(String line) {
        this.line = line;
    }

    static LineParser fromString(String line) {
        return new LineParser(line);
    }

    String toLine() {
        return line;
    }

    LineParser insertTextFormattingTags() {
        this.line = TextFormatting.insertTextFormattingTags(line);
        return this;
    }

    LineParser insertLineFormattingTags(){
        this.line= LineFormatting.insertLineFormattingTags(line);
        return this;
    }
}
