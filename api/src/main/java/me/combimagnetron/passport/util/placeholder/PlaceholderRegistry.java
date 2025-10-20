package me.combimagnetron.passport.util.placeholder;

import me.combimagnetron.passport.util.matcher.MatcherSection;
import me.combimagnetron.passport.util.matcher.MatcherToken;
import me.combimagnetron.passport.util.matcher.TokenMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface PlaceholderRegistry {

    <T> void register(PlaceholderProvider<T> placeholder);

    <T> String parse(Placeholder<T> placeholder);

    Collection<PlaceholderProvider<?>> placeholders();

    class Impl implements PlaceholderRegistry {
        private final Set<PlaceholderProvider<?>> placeholders = new HashSet<>();

        @Override
        public <T> void register(PlaceholderProvider<T> placeholder) {
            placeholders.add(placeholder);
        }

        @Override
        public <T> String parse(Placeholder<T> placeholder) {
            for (PlaceholderProvider<?> provider : placeholders) {
                PlaceholderProvider<T> cast = (PlaceholderProvider<T>) provider;
                if (!TokenMatcher.matcher(placeholder.placeholder()).section(MatcherSection.section().token(MatcherToken.required(cast.format()))).validate().empty()) {
                    return cast.parse(placeholder);
                }
            }
            return placeholder.placeholder();
        }

        @Override
        public Collection<PlaceholderProvider<?>> placeholders() {
            return placeholders;
        }
    }

}
