package me.combimagnetron.passport.util.matcher;

import me.combimagnetron.passport.util.Values;

import java.util.regex.Pattern;

public record Token(Type type, String captured) {

    public interface Type {
        Type OBJECT = Impl.of("([A-Z]\\w*)");
        Type KEYWORD = Impl.of("(\\b[a-z]+\\b)");
        Type METHOD = Impl.of("(((repeated |responseless )?message ([A-Z]\\w*)(\\(( ?\\w+ \\w+,?)+\\)))|(type \\w* \\{\\s*(\\w+ \\w+,?\\s*)+\\}))");
        Type KEYWORD_OBJECT_PAIR = Impl.of("([A-Z]\\w* \\w+)");
        Type BRACKET_OPEN = Impl.of("\\{");
        Type BRACKET_CLOSE = Impl.of("\\]");
        Type CLASS = Impl.of("((namespace \\w+;)(service \\w+ )(extends [a-zA-Z_]+ )(\\{\\s*[\\s\\S]+\\}))");
        Type NUMBER = Impl.of("[\\d\\.]+");
        Type COMMENT = Impl.of("//.*");
        Type COMMENT_BLOCK = Impl.of("((\\/\\*)[\\s\\S]*(\\*\\/))");
        Values<Type> VALUES = Values.of(OBJECT, KEYWORD, METHOD, NUMBER, CLASS, COMMENT, COMMENT_BLOCK);

        Pattern pattern();

        static Type of(String literalPattern) {
            return Impl.of(literalPattern);
        }

        record Impl<T>(String literalPattern) implements Type {
            public static <T> Impl<T> of(String literalPattern) {
                return new Impl<>(literalPattern);
            }

            @Override
            public Pattern pattern() {
                return Pattern.compile(literalPattern);
            }
        }

    }

}
