package me.combimagnetron.passport.util.condition;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public interface Operator {

    Map<String, Class<? extends Operator>> operators = Map.of(
            "==", EqualsOperator.class, "!=", NotEqualsOperator.class, ">", GreaterThanOperator.class,
            ">=", GreaterThanOrEqualsOperator.class, "<", LessThanOperator.class, "<=", LessThanOrEqualsOperator.class
    );

    String operator();

    default String operatorWithSpaces() {
        return " " + operator() + " ";
    }

    Condition.Result eval(Object first, Object second);

    Set<Class<?>> applicableTo();

    static Operator find(String string) {
        try {
            return operators.get(string.split(" ")[1]).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    class EqualsOperator implements Operator {

        @Override
        public String operator() {
            return "==";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of(first == second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return null;
        }
    }

    class NotEqualsOperator implements Operator {
        @Override
        public String operator() {
            return "!=";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of(first != second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return null;
        }
    }

    class GreaterThanOperator implements Operator {

        @Override
        public String operator() {
            return ">";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of((Double) first > (Double) second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return Set.of(double.class, int.class, short.class, float.class, Number.class);
        }
    }

    class GreaterThanOrEqualsOperator implements Operator {
        @Override
        public String operator() {
            return ">=";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of((Double) first >= (Double) second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return Set.of(double.class, int.class, short.class, float.class, Number.class);
        }
    }

    class LessThanOperator implements Operator {
        @Override
        public String operator() {
            return "<";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of((Double) first < (Double) second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return Set.of(double.class, int.class, short.class, float.class, Number.class);
        }
    }

    class LessThanOrEqualsOperator implements Operator {
        @Override
        public String operator() {
            return "<=";
        }

        @Override
        public Condition.Result eval(Object first, Object second) {
            return Condition.Result.of((Double) first <= (Double) second, "");
        }

        @Override
        public Set<Class<?>> applicableTo() {
            return Set.of(double.class, int.class, short.class, float.class, Number.class);
        }
    }




}
