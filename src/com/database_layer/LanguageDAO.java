package com.database_layer;

import java.sql.*;

public class LanguageDAO {
    public static int getLanguageIndexFromDataBase() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        StringBuilder sb = new StringBuilder("SELECT * FROM languagePreference");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sb.toString());
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }
        return 0;
    }

    public static void insertSelectedLanguageIndexInDatabase(int newIndex) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder("TRUNCATE TABLE languagePreference");
        StringBuilder sb2 = new StringBuilder("INSERT INTO languagePreference VALUES (" + newIndex + ")");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.executeUpdate();
            statement = connection.prepareStatement(sb2.toString());
            statement.executeUpdate();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
