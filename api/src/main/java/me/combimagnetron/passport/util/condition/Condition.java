package me.combimagnetron.passport.util.condition;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public interface Condition {

    Result eval(Supplier<?>... value);

    record Result(boolean value) {
        public static Result of(boolean value, String reason) {
            return new Result(value);
        }
    }

    static <T> Condition of(String condition) {
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
        private Method method;

        SimpleCondition(String condition) throws ReflectiveOperationException {
            this.operator = Operator.find(condition);
            this.condition = adaptString(condition);
        }

        private String adaptString(String string) {
            if (string.contains("().")) {
                return string;
            }
            String[] evalSplit = string.split(operator.operator() + " ");
            String parts = evalSplit[0].replaceAll("\\.", "().").replace(" ", "()");;
            return String.join("", parts) + " " + operator.operator() + " " + evalSplit[1];
        }

        private Method findMethod(Supplier<?>... value) throws NoSuchMethodException {
            String[] path = condition.split(operator.operatorWithSpaces())[0].split("\\(\\)\\.");
            String variableName = value[0].value().getClass().getName().toLowerCase();
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
        public Result eval(Supplier<?>... value) {
            try {
                this.method = findMethod(value);
                ConditionTypeAdapter<?> typeAdapter = ConditionTypeAdapter.find(this.method.getReturnType());
                Object object = typeAdapter.get().apply(condition.split(operator.operatorWithSpaces())[1]);
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
        public Result eval(Supplier<?>... value) {
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
