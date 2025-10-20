package me.combimagnetron.passport.util.condition;

import me.combimagnetron.passport.Passport;
import me.combimagnetron.passport.util.matcher.MatcherSection;
import me.combimagnetron.passport.util.matcher.MatcherToken;
import me.combimagnetron.passport.util.matcher.TokenMatcher;
import me.combimagnetron.passport.util.placeholder.Placeholder;
import me.combimagnetron.passport.util.placeholder.PlaceholderProvider;
import me.combimagnetron.passport.util.placeholder.PlaceholderRegistry;

import java.lang.reflect.Method;
import java.util.*;

public interface Condition {

    <T> Result eval(Supplier<?>... value);

    record Result(boolean value) {
        public static Result of(boolean value, String reason) {
            return new Result(value);
        }
    }

    static Condition of(String condition) {
        try {
            if (condition.contains(" && ")) {
                return new ComplexCondition(condition);
            }
            return new SimpleCondition(condition);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    class SimpleCondition implements Condition {
        private final String condition;
        private final Operator operator;
        private PlaceholderProvider<?> placeholderProvider;
        private Method method;

        SimpleCondition(String condition) throws ReflectiveOperationException {
            this.operator = Operator.find(condition);
            this.condition = adaptString(condition);
        }

        private String adaptString(String string) {
            if (string.contains("().")) {
                return string;
            }
            String[] evalSplit = string.split(operator.operatorWithSpaces());
            String parts = evalSplit[0].replaceAll("\\.", "().").replace(" ", "()");;
            return String.join("", parts) + " " + operator.operator() + " " + evalSplit[1];
        }

        private Method findMethod(Supplier<?>... value) throws NoSuchMethodException {
            String[] path = condition.split(operator.operatorWithSpaces())[0].split("\\(\\)\\.");
            String variableName = value[0].value().getClass().getName().toLowerCase();
            String[] evalSplit = condition.split(" " + operator.operator() + " ");
            for (String s : evalSplit) {
                PlaceholderRegistry placeholderRegistry = Passport.passport().placeholders();
                for (PlaceholderProvider<?> provider : placeholderRegistry.placeholders()) {
                    if (!TokenMatcher.matcher(s).section(MatcherSection.section().token(MatcherToken.required(provider.format()))).validate().empty()) {
                        this.placeholderProvider = provider;
                        return PlaceholderProvider.class.getDeclaredMethod("parse", Placeholder.class);
                    }
                }
            }
            if (!path[0].equals(variableName)) {
                return null;
            }
            Method lastMethod = null;
            for (String s : path) {
                if (s.equals(variableName)) {
                    continue;
                }
                if (lastMethod == null) {
                    lastMethod = value[0].value().getClass().getDeclaredMethod(s);
                }
                lastMethod = lastMethod.getClass().getDeclaredMethod(s);
            }
            return lastMethod;
        }

        @Override
        public <T> Result eval(Supplier<?>... value) {
            try {
                this.method = findMethod(value);
                Object object;
                if (this.method.getDeclaringClass() == PlaceholderProvider.class) {
                    PlaceholderProvider<T> cast = (PlaceholderProvider<T>) placeholderProvider;
                    object = ConditionTypeAdapter.STRING.get().apply(condition.split(operator.operatorWithSpaces())[1], null);
                    Placeholder<T> placeholder = Placeholder.of((T) value[0].value(), condition.split(operator.operatorWithSpaces())[0]);
                    System.out.println("." + cast.parse(placeholder) + ". ." + object + ".");
                    return operator.eval(cast.parse(placeholder), object);
                } else {
                    ConditionTypeAdapter<?> typeAdapter = ConditionTypeAdapter.find(this.method.getReturnType());
                    object = typeAdapter.get().apply(condition.split(operator.operatorWithSpaces())[1], null);
                }
                return operator.eval(this.method.invoke(value[0].value()), object);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ComplexCondition implements Condition {
        private final Collection<SimpleCondition> simpleConditions = new HashSet<>();
        private final int amount;


        ComplexCondition(String condition) throws ReflectiveOperationException {
            String[] conditions = condition.split(" && ");
            this.amount = conditions.length;
            for (String c : conditions) {
                simpleConditions.add(new SimpleCondition(c));
            }
        }

        @Override
        public <T> Result eval(Supplier<?>... value) {
            if (value.length > amount) {
                return null;
            }
            Set<Boolean> result = new LinkedHashSet<>();
            int i = 0;
            for (Condition condition : simpleConditions) {
                result.add(condition.eval(value[i]).value());
                i++;
            }
            for (boolean bool : result) {
                if (!bool) {
                    return Result.of(false, "");
                }
            }
            return Result.of(true, "All conditions passed.");
        }
    }



}
