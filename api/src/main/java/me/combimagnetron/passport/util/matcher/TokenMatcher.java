package me.combimagnetron.passport.util.matcher;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public interface TokenMatcher {

    TokenMatcher section(MatcherSection section);

    TokenizedResult validate();

    static TokenMatcher matcher(String content) {
        return new Impl(content);
    }

    class Impl implements TokenMatcher {
        private final Collection<MatcherSection> sections = new LinkedHashSet<>();
        private final String content;

        protected Impl(String content) {
            this.content = content;
        }

        @Override
        public TokenMatcher section(MatcherSection section) {
            sections.add(section);
            return this;
        }

        private MatcherSection combine() {
            MatcherSection empty = MatcherSection.section();
            for (MatcherSection section : sections) {
                MatcherSection.Impl impl = (MatcherSection.Impl) section;
                impl.indexedTokens().forEach((index, token) -> {
                    if (index < 0) {
                        empty.token(token);
                        return;
                    }
                    empty.token(token);
                });
            }
            return empty;
        }

        @Override
        public TokenizedResult validate() {
            Map<TokenizedResult.TokenizedResultEntry, String> entries = new LinkedHashMap<>();
            MatcherSection.Impl section = (MatcherSection.Impl) combine();
            String content = this.content;
            int i = 0;
            for (var entry : section.indexedTokens().entrySet()) {
                MatcherToken token = entry.getValue();
                Token.Type type = token.token();
                Matcher matcher = type.pattern().matcher(content);
                if (entry.getKey() < 0) {
                    List<MatchResult> resultStream = matcher.results().toList();
                    int amount = resultStream.size();
                    Map<TokenizedResult.TokenizedResultEntry, String> multipleMap = new LinkedHashMap<>(Maps.subMap(new TreeMap<>(entries), Range.closed(new TokenizedResult.TokenizedResultEntry(0, null), new TokenizedResult.TokenizedResultEntry(i++, null))));
                    for (MatchResult result : resultStream) {
                        multipleMap.put(new TokenizedResult.TokenizedResultEntry(amount++, type), result.group());
                    }
                    entries = multipleMap;
                } else {
                    Optional<MatchResult> optionalResult = matcher.results().findFirst();
                    if (optionalResult.isEmpty() && token instanceof MatcherToken.RequiredMatcherToken) {
                        continue;
                    } else if (optionalResult.isEmpty()) {
                        throw new SatelliteSyntaxError("Token not found: " + type);
                    }
                    String result = optionalResult.get().group();
                    entries.put(new TokenizedResult.TokenizedResultEntry(entry.getKey(), type), result);
                    content = content.replace(content.substring(matcher.start(), matcher.end()), "");
                    i++;
                }
            }
            return new TokenizedResult.Impl(entries);
        }

    }

    class SatelliteSyntaxError extends RuntimeException {
        public SatelliteSyntaxError(String message) {
            super(message);
        }
    }


}
