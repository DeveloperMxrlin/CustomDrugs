package mxrlin.customdrugs.helper.mysql;

import org.bukkit.configuration.file.FileConfiguration;

public class MySQLFile {

    public static boolean useMySQL;
    private static String host;
    private static String port;
    private static String database;
    private static String username;
    private static String password;


    public static boolean loadMySQL(FileConfiguration configuration){

        useMySQL = configuration.getBoolean("mysql");

        if(useMySQL){

            host = configuration.getString("host");
            port = configuration.getString("port");
            database = configuration.getString("database");
            username = configuration.getString("username");
            password = configuration.getString("password");

            if(MySQL.connect()){

                return true;

            }

        }

        return false;

    }

    public static boolean disconnectMySQL(){

        if(useMySQL && MySQL.isConnected()){

            if(MySQL.disconnect()){

                return true;

            }

        }

        return false;

    }

    public static String getHost() {
        return host;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
