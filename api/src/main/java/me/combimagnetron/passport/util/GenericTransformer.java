package me.combimagnetron.passport.util;

public interface GenericTransformer<V, T> {

    V transform(T t);

}
