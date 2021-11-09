package de.hse.swt.timemanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
                    "SELECT id, firstName, lastName, email, password, vacationdays, hierarchy, groupid FROM user WHERE email = ?");
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
                        if (hierarchy.equals("supervisor"))
                            mainController.showSupervisorMenu();
                        mainController.setNames(firstName, lastname, vacDays);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
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

    public static void getDataOfDate(LocalDate localeDate) throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection.prepareStatement(
                    "SELECT worktime, start, end, break, vacation, illness FROM timetable_" + id + " WHERE date = ?");
            preparedStatement.setString(1, localeDate.toString());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println(localeDate + ": No data found.");
                mainController.setTimes("00:00:00", "00:00:00");
            } else {
                while (resultSet.next()) {
                    System.out.println(localeDate + ": data found.");
                    String worktime = resultSet.getString("worktime");
                    String breaktime = resultSet.getString("break");
                    mainController.setTimes(worktime, breaktime);
                }
            }
        } catch (SQLException e) {
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

    public static void compareData(LocalDate selectedDate, String newWorktime, String newBreaktime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection.prepareStatement(
                    "SELECT worktime, break, vacation, illness FROM timetable_" + id + " WHERE date = ?");
            preparedStatement.setString(1, selectedDate.toString());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No data found.");
            } else {
                while (resultSet.next()) {
                    String worktime = resultSet.getString("worktime");
                    String breaktime = resultSet.getString("break");
                    Boolean wkt = newWorktime.equals(worktime);
                    Boolean bkt = newBreaktime.equals(breaktime);

                    if (wkt && bkt) {
                        System.out.println("There is no new Data");
                    } else {
                        System.out.println("There is new data");
                        Statement statement = connection.createStatement();

                        int updateCount = statement.executeUpdate("UPDATE timetable_" + id + " SET worktime='"
                                + newWorktime + "' WHERE date = '" + selectedDate + "'");
                        updateCount += statement.executeUpdate("UPDATE timetable_" + id + " SET break = '"
                                + newBreaktime + "' WHERE date = '" + selectedDate + "'");

                        System.out.println("Updated timetable_" + id + " successfully : " + updateCount + " Times");

                        mainController.setTimes(newWorktime, newBreaktime);
                    }
                }
            }
        } catch (SQLException e) {
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
        vacDays = resultset.getInt("vacationdays");
        hierarchy = resultset.getString("hierarchy");
        groupId = resultset.getInt("GroupId");
    }

}
