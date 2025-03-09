package me.combimagnetron.passport.data.type;

public interface DataType<T> {

    Class<T> type();

    DataType<String> STRING = new SimpleDataType<>(String.class);
    DataType<Integer> INTEGER = new SimpleDataType<>(Integer.class);
    DataType<Double> DOUBLE = new SimpleDataType<>(Double.class);
    DataType<Short> SHORT = new SimpleDataType<>(Short.class);
    DataType<Byte> BYTE = new SimpleDataType<>(Byte.class);


    record SimpleDataType<T>(Class<T> type) implements DataType<T> {

    }


}
