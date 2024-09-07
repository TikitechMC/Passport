package me.combimagnetron.passport.util.matcher;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface MatcherSection {

    MatcherSection token(MatcherToken token);

    static MatcherSection section() {
        return new Impl();
    }


    class Impl implements MatcherSection {
        private final HashMap<Integer, MatcherToken> indexedTokens = new LinkedHashMap<>();
        private int i = 0;

        @Override
        public MatcherSection token(MatcherToken token) {
            if (token instanceof MatcherToken.MultipleMatcherToken) {
                indexedTokens.put(-i, token);
                return this;
            }
            indexedTokens.put(i++, token);
            return this;
        }

        public HashMap<Integer, MatcherToken> indexedTokens() {
                return indexedTokens;
            }

    }

    }