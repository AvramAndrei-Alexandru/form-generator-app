package com.database_layer;

import com.mysql.cj.jdbc.DatabaseMetaData;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormDao {
    public static boolean insert(String name, InputStream doc){
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder("INSERT INTO `forms` (`form_name`, `form`) VALUES (?, ?)");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.setString(1, name);
            statement.setBlob(2, doc );
            int row = statement.executeUpdate();
            if(row > 0){
                result = true;
            }
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return result;
    }

    public static InputStream verifyForm(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder sb = new StringBuilder("SELECT `form` FROM `forms` WHERE `form_name` = '" + name + "'");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBinaryStream(1);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }
        return null;
    }

    public static List<InputStream> getAllTheForms() {
        List<InputStream> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder sb = new StringBuilder("SELECT `form` FROM `forms`");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result.add(resultSet.getBinaryStream(1));
            }
           return result;
        } catch (SQLException | NullPointerException e ) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }
        return null;

    }

    public static void deleteFormAnswerTable(String name) {
        Connection connection = null;
        Statement statement = null;
        String query = "DROP TABLE " + name  ;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException | NullPointerException e ) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public static void deleteFormFromTable(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM forms WHERE form_name = '" + name + "'" ;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException | NullPointerException e ) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

}
