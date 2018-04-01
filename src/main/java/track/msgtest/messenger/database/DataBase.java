package track.msgtest.messenger.database;

import java.sql.*;
import java.util.List;

/**
 * Created by oleg on 13.06.17.
 */
public class DataBase {

    private String url = "jdbc:mysql://127.0.0.1:3306/messanger_java";
    private String name = "root";
    private String password = "";

    private Connection connection = null;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, name, password);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createChat(String name, List<String> membersChat) throws DataBaseException {
        Statement statement = null;
        try {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new DataBaseException("Invalid connection");
            }
            String sql = "INSERT INTO chats (id, name) " +
                    "VALUES (0,'" + name + "');";
            try {
                statement.execute(sql);
            } catch (SQLException e) {
                throw new DataBaseException("Invalid name of chat");
            }
            sql = "SELECT id FROM chats WHERE name='" + name + "';";
            ResultSet resultSet = null;
            int chatId = -1;
            try {
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                chatId = resultSet.getInt("id");
            } catch (SQLException e) {
                throw new DataBaseException("Error while getting id of chart");
            }
            for (String member : membersChat) {
                sql = "SELECT id FROM users WHERE name='" + member + "';";
                int userId = -1;
                try {
                    resultSet = statement.executeQuery(sql);
                    userId = resultSet.getInt("id");
                } catch (SQLException e) {
                    throw new DataBaseException("Invalid name of member");
                }
                sql = "INSERT INTO chat_membership (chat_id, user_id) " +
                        "VALUES(" + chatId + "," + userId + ");";
                try {
                    statement.execute(sql);
                } catch (SQLException e) {
                    throw new DataBaseException("Error while adding user");
                }

            }
        } finally {
            try {
                statement.close();
            } catch (SQLException e) { }
        }
    }

    public void createUser(String name, int age) throws DataBaseException {
        Statement statement = null;
        try {
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                throw new DataBaseException("Error while creating user");
            }
            String sql = "INSERT INTO users (name, age) " +
                    "VALUES ('" + name + "'," + age + ");";
            try {
                statement.execute(sql);
            } catch (SQLException ex) {
                throw new DataBaseException("Invalid name of users");
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) { }
        }
    }

    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        dataBase.connect();
        try {
            dataBase.createChat("123", null);
        } catch (DataBaseException e) {
            System.out.println(e);
        }
        dataBase.disconnect();
    }

}
