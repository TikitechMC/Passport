package me.combimagnetron.passport.util.matcher;

public interface MatcherToken {
    Token.Type token();

    static MatcherToken required(Token.Type token) {
        return new RequiredMatcherToken(token);
    }

    static MatcherToken optional(Token.Type token) {
        return new OptionalMatcherToken(token);
    }

    static MatcherToken multiple(Token.Type token) {
        return new MultipleMatcherToken(token);
    }

    record RequiredMatcherToken(Token.Type token) implements MatcherToken {

    }

    record OptionalMatcherToken(Token.Type token) implements MatcherToken {

    }

    record MultipleMatcherToken(Token.Type token) implements MatcherToken {

    }

}