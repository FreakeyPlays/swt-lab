package de.hse.swt.timemanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class DBUtils {
    static int id;
    static String firstName;
    static String lastname;
    static String eMail;
    static String hierarchy;
    static int groupId;
    static int vacDays;

    public static void logInUser(String email, String password) throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection.prepareStatement(
                    "SELECT id, firstName, lastName, password, email, hierarchy, GroupId FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");

                    if (retrievedPassword.equals(password)) {
                        setUser(resultSet);
                        App.setRoot("main", 1280, 720);
                        mainController.setNames(firstName, lastname);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No connection to the Database!");
            alert.show();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setUser(ResultSet resultset) throws SQLException {
        id = resultset.getInt("id");
        firstName = resultset.getString("firstName");
        lastname = resultset.getString("lastName");
        eMail = resultset.getString("email");
        hierarchy = resultset.getString("hierarchy");
        groupId = resultset.getInt("GroupId");
    }

}
