package me.combimagnetron.passport.util.placeholder;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.util.matcher.Token;

public interface PlaceholderProvider<T> {

    Identifier identifier();

    Token.Type format();

    <V> V parse(Placeholder<T> placeholder);

}
