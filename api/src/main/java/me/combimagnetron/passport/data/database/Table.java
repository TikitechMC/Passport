package me.combimagnetron.passport.data.database;

public interface Table {

    Table insert(Row<?> row);

    Table put(Object... objects);


}
