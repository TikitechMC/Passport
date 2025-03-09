package me.combimagnetron.passport.data;

public interface Type<T> {

    byte[] serialize();

    Class<T> type();

}
