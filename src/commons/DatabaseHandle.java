package com.netflix.commons;

public class DatabaseHandle {
    public String generateConnectionString() {
        return "jdbc:sqlserver://"
                + PropertyIndex.get("jdbc.server")
                + ":"
                + PropertyIndex.get("jdbc.port")
                + ";database="
                + PropertyIndex.get("jdbc.database")
                + ";user="
                + PropertyIndex.get("jdbc.user")
                + ";password="
                + PropertyIndex.get("jdbc.password");
    }

}
