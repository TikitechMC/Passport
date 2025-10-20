package me.combimagnetron.passport.data.database;

import me.combimagnetron.passport.data.Identifier;

public interface Database {

    Summary save(DataStorageArguments arguments);

    Summary save();

    Table table(Identifier identifier);

    Query query(Statement statement);

    interface DataStorageArguments {

        Identifier identifier();

    }

    record Summary(boolean success, long average, long total) {

        public static Summary failed(long total, int tasks) {
            return new Summary(false, total / tasks, total);
        }

        public static Summary success(long total, int tasks) {
            return new Summary(true, total / tasks, total);
        }


    }

}
