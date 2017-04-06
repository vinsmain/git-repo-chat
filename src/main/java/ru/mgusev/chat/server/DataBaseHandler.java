package ru.mgusev.chat.server;

import ru.mgusev.chat.client.model.AuthMessage;
import ru.mgusev.chat.client.model.AuthResult;
import ru.mgusev.chat.client.model.RegisterMessage;
import java.sql.*;

public class DataBaseHandler {

    private static Connection conn;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psUpdate;
    private static PreparedStatement psAuthorisation;
    private static PreparedStatement psDelete;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/ChatServerDB.db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NickName TEXT UNIQUE NOT NULL, Login TEXT UNIQUE NOT NULL, Password TEXT NOT NULL)");
            psRegistration = conn.prepareStatement("INSERT INTO Users(NickName, Login, Password) VALUES(?, ?, ?)");
            /*psUpdate = conn.prepareStatement("UPDATE Students SET Score= ? WHERE Name = ?");*/
            psAuthorisation = conn.prepareStatement("SELECT * FROM Users WHERE Login = ? AND Password = ?");
            /*psDelete = conn.prepareStatement("DELETE FROM Students WHERE Name = ?");*/
        } catch (Exception e) {
            System.out.println("Ошибка инициализации JDBC драйвера");
            e.printStackTrace();
        }
    }

    public static void registration(RegisterMessage registerMessage) {
        try {
            psRegistration.setString(1, registerMessage.getNickName());
            psRegistration.setString(2, registerMessage.getLogin());
            psRegistration.setString(3, registerMessage.getPassword());
            psRegistration.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка регистрации");
            //e.printStackTrace();
        }
    }

    public static AuthResult authorisation(AuthMessage authMessage) {
        try {
            psAuthorisation.setString(1, authMessage.getLogin());
            psAuthorisation.setString(2, authMessage.getPassword());
            ResultSet authResultSet = psAuthorisation.executeQuery();
            return new AuthResult(authResultSet.getString(2));
        } catch (SQLException e) {
            return new AuthResult(null);
        }
    }

    /*public static boolean update(String name, int score) {
        try {
            psUpdate.setInt(1, score);
            psUpdate.setString(2, name);
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap read(String name) {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        try {
            psRead.setString(1, name);
            ResultSet rs = psRead.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }*/

    public static void delete(String name) {
        try {
            psDelete.setString(1, name);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
