package me.combimagnetron.passport.data.database;

import me.combimagnetron.passport.data.Row;

public interface Table {

    Table insert(Row<?> row);

    Table put(Object... objects);


}
