package com.database_layer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AnswerDao {
    public static boolean createTable(String name, Map<String, Integer> tableHeaders){
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder("CREATE TABLE `form_database`.`"+name+"` (`id"+name+"` INT NOT NULL");
        for (String col: tableHeaders.keySet()) {
            sb.append(", `"+col+"` VARCHAR(" + tableHeaders.get(col) + ") NULL");
        }
        sb.append(", PRIMARY KEY (`id"+name+"`))");
                try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
                return result;
    }
}
