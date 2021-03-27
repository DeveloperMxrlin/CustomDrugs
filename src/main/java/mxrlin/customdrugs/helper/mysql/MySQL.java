package mxrlin.customdrugs.helper.mysql;

import java.sql.*;

public class MySQL {

    /*
        MySQL.update("INSERT INTO tablename (arg1, arg2,...) VALUES ('arg1 value', 'arg2 value',...)");

        --> Add a value in the table

        --------------------------------------------------------

        MySQL.update("DELETE FROM tablename WHERE arg1='target'");

        --> Delete a value in the table

        --------------------------------------------------------

        ResultSet rs = MySQL.getResult("SELECT arg1 FROM tablename WHERE args='target'");
        try {
            if(rs.next()) {
                return rs.getString("arg1");
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch(Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";

        --> Get a value

        --------------------------------------------------------

        List<String> reports = new ArrayList<>();
        ResultSet rs = MySQL.getResult("SELECT * FROM tablename");

        try {
            while(rs.next()) {
                reports.add("Arg1: " + rs.getInt(arg1) + " Arg2: " + rs.getString(arg2));
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch(Exception exception2) {
                exception2.printStackTrace();
            }
        }
        return reports;

        --> Get everything in a table in form of a List

        --------------------------------------------------------

     */

    static String host = MySQLFile.getHost();
    static String port = MySQLFile.getPort();
    static String database = MySQLFile.getDatabase();
    static String username = MySQLFile.getUsername();
    static String password = MySQLFile.getPassword();
    static Connection con;

    public static boolean connect(){
        if(!isConnected()){
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,username,password);
                createTables();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    public static boolean disconnect(){
        if(isConnected()){
            try {
                con.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    public static boolean isConnected(){
        return (con != null);
    }


    public static void update(String qry){
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static ResultSet getResult(String qry){
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static void createTables() {
        if(!isConnected()) return;
        update("CREATE TABLE IF NOT EXISTS drugs (ID INT(100) PRIMARY KEY AUTO_INCREMENT NOT NULL, Name VARCHAR(100), Description VARCHAR(100), Effect1 VARCHAR(100), Effect2 VARCHAR(100), Effect3 VARCHAR(100), Duration INT(100), SellPrice INT(100), BuyPrice INT(100));");
    }
}
