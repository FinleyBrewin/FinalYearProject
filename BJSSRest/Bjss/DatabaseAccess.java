package com.BJSS.BJSSRest.Bjss;

import java.util.ArrayList;
import java.sql.*;

public class DatabaseAccess {
    public ArrayList<String> SelectAzureSQL(String username, String password, String sql, String cnnStr) {
        // return reseult from a select statement
        ResultSet resultSet = null;
        ArrayList<String> People = new ArrayList<>();
        try (Connection cnn = DriverManager.getConnection(cnnStr);
                Statement statement = cnn.createStatement();) {
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String Person = "";
                Person = (resultSet.getString(1) + "&" + resultSet.getString(2) +
                        "&" + resultSet.getString(3) + "&" + resultSet.getString(4) +
                        "&" + resultSet.getString(5) + "&" + resultSet.getString(6) +
                        "&" + resultSet.getString(7) + "&" + resultSet.getString(8) +
                        "&" + resultSet.getString(9));
                People.add(Person);
            }
        } catch (SQLException e) {
            System.out.println();
        }
        return People;
    }
}
