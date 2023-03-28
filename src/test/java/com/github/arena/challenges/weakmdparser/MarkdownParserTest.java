package com.github.arena.challenges.weakmdparser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MarkdownParserTest {

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                arguments("This will be a paragraph", "<p>This will be a paragraph</p>"),
                arguments("_This will be italic_", "<p><em>This will be italic</em></p>"),
                arguments("__This will be bold__", "<p><strong>This will be bold</strong></p>"),
                arguments("This will _be_ __mixed__", "<p>This will <em>be</em> <strong>mixed</strong></p>"),
                arguments("# This will be an h1", "<h1>This will be an h1</h1>"),
                arguments("## This will be an h2", "<h2>This will be an h2</h2>"),
                arguments("###### This will be an h6",  "<h6>This will be an h6</h6>"),
                arguments("* Item 1\n* Item 2","<ul><li>Item 1</li><li>Item 2</li></ul>"),
                arguments("# Header!\n* __Bold Item__\n* _Italic Item_","<h1>Header!</h1><ul><li><strong>Bold Item</strong></li><li><em>Italic Item</em></li></ul>"),
                arguments("# This is a header with # and * in the text", "<h1>This is a header with # and * in the text</h1>"),
                arguments("* Item 1 with a # in the text\n* Item 2 with * in the text", "<ul><li>Item 1 with a # in the text</li><li>Item 2 with * in the text</li></ul>"),
                arguments("This is a paragraph with # and * in the text", "<p>This is a paragraph with # and * in the text</p>"),
                arguments("# Start a list\n* Item 1\n* Item 2\nEnd a list", "<h1>Start a list</h1><ul><li>Item 1</li><li>Item 2</li></ul><p>End a list</p>")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void shouldTranslateStringWithMarkdownSyntaxIntoHtmlString(String input, String expected) {
        assertEquals(expected, MarkdownParser.parse(input));
    }
}