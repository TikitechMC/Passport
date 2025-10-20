package me.combimagnetron.passport.data.database;

public interface Type<T> {

    byte[] serialize();

    Class<T> type();

}
