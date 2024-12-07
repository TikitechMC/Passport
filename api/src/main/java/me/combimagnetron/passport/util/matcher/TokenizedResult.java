package me.combimagnetron.passport.util.matcher;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public interface TokenizedResult {

    String content(Token.Type type);

    String content(int index);

    Token.Type token(int index);

    default boolean empty() {
        return ordered().all().isEmpty();
    }

    Collection<MatcherResult> all(Token.Type type);

    OrderedView ordered();

    record TokenizedResultEntry(int index, Token.Type token) implements Comparable<TokenizedResultEntry> {

        @Override
        public int compareTo(@NotNull TokenizedResult.TokenizedResultEntry o) {
            return index - o.index();
        }
    }

    class Impl implements TokenizedResult {
        private final LinkedHashMap<TokenizedResultEntry, String> entries = new LinkedHashMap<>();

        protected Impl(Map<TokenizedResultEntry, String> entries) {
            this.entries.putAll(entries);
        }

        @Override
        public String content(Token.Type type) {
            return entries.entrySet().stream().filter(entry -> entry.getKey().token().equals(type)).findFirst().map(Map.Entry::getValue).orElse(null);
        }

        @Override
        public Collection<MatcherResult> all(Token.Type type) {
            return entries.entrySet().stream().filter(entry -> entry.getKey().token().equals(type)).map(entry -> new MatcherResult(new Token(entry.getKey().token(), entry.getValue()))).collect(Collectors.toList());
        }

        @Override
        public String content(int index) {
            return findByIndex(index).orElseThrow().getValue();
        }

        private Optional<Map.Entry<TokenizedResultEntry, String>> findByIndex(int index) {
            return entries.entrySet().stream().filter(entry -> entry.getKey().index() == index).findFirst();
        }

        @Override
        public Token.Type token(int index) {
            return findByIndex(index).orElseThrow().getKey().token();
        }

        @Override
        public OrderedView ordered() {
            return new OrderedView(entries);
        }
    }

    class OrderedView {
        private final LinkedHashMap<TokenizedResultEntry, String> entries = new LinkedHashMap<>();
        private final Iterator<Map.Entry<TokenizedResultEntry, String>> iterator;

        protected OrderedView(LinkedHashMap<TokenizedResultEntry, String> entries) {
            this.entries.putAll(entries);
            this.iterator = entries.entrySet().iterator();
        }

        public MatcherResult next() {
            Map.Entry<TokenizedResultEntry, String> entry = iterator.next();
            return new MatcherResult(new Token(entry.getKey().token(), entry.getValue()));
        }

        public Collection<MatcherResult> all() {
            return entries.entrySet().stream().map(entry -> new MatcherResult(new Token(entry.getKey().token(), entry.getValue()))).collect(Collectors.toList());
        }


    }

    record MatcherResult(Token token) {

        public TokenMatcher matcher() {
            return new TokenMatcher.Impl(token.captured());
        }

    }

}
