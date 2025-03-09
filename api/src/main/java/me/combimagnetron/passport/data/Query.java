package me.combimagnetron.passport.data;

public interface Query {

    Field find(String name);

    Row get(int index);

    Column get(String name);

    interface Field {

    }

    interface Row {

    }

    interface Column {

    }

}
