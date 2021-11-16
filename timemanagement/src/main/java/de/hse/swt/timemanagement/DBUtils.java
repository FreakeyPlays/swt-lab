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

    public static User usr;

    /**
     * This Function takes in {@code email} and {@code password} and makes a
     * connection to the Database. It Tryes to login the user with the provided
     * credentials and pulls all Data into a User.
     * 
     * @param email    of the User
     * @param password of the User
     * @throws IOException
     */
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
                        usr = new User(resultSet.getInt("id"), resultSet.getString("firstName"),
                                resultSet.getString("lastName"), resultSet.getString("email"),
                                resultSet.getString("hierarchy"), resultSet.getInt("groupid"),
                                resultSet.getInt("vacationdays"));
                        App.setRoot("main", 1280, 720);
                        if (usr.getHierarchy().equals("supervisor"))
                            mainController.showSupervisorMenu();
                        mainController.setNames(usr.getFirstName(), usr.getLastName(), usr.getVacDays());
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

    /**
     * This Function takes in the {@code localeData} and tryes to get data of the
     * Database. If it found Data itgets Displayed int the Labels
     * 
     * @param localeDate the selected Date of the Date Picker
     * @throws IOException
     */
    public static void getDataOfDate(LocalDate localeDate) throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection
                    .prepareStatement("SELECT worktime, start, end, break, vacation, illness FROM timetable_"
                            + usr.getID() + " WHERE date = ?");
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

    /**
     * This function takes {@code selectedDate}, {@code newWorktime} and
     * {@code newBreaktime} and cheks if the Data has changend.
     * 
     * @param selectedDate
     * @param newWorktime
     * @param newBreaktime
     */
    public static void compareData(LocalDate selectedDate, String newWorktime, String newBreaktime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement st = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection.prepareStatement(
                    "SELECT worktime, break, vacation, illness FROM timetable_" + usr.getID() + " WHERE date = ?");
            preparedStatement.setString(1, selectedDate.toString());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                st = connection.createStatement();
                st.executeUpdate("INSERT INTO timetable_" + usr.getID()
                        + " (userid, date, worktime, start, end, break, vacation, illness)" + "VALUES (" + usr.getID()
                        + ", '" + selectedDate.toString() + "', '" + newWorktime + "', 'edited', 'edited', '"
                        + newBreaktime + "', 0, 0)");
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

                        int updateCount = statement.executeUpdate("UPDATE timetable_" + usr.getID() + " SET worktime='"
                                + newWorktime + "' WHERE date = '" + selectedDate + "'");
                        updateCount += statement.executeUpdate("UPDATE timetable_" + usr.getID() + " SET break = '"
                                + newBreaktime + "' WHERE date = '" + selectedDate + "'");

                        System.out.println(
                                "Updated timetable_" + usr.getID() + " successfully : " + updateCount + " Times");

                        mainController.setTimes(newWorktime, newBreaktime);
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

            if (st != null) {
                try {
                    st.close();
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

    /**
     * This function takes in an Array of {@code newusrData} and addes this to the
     * users Database. It also creates a new timetable database for each User.
     * 
     * @param newUsrData
     */
    public static void addUserToDB(String[] newUsrData) {

        if (!doesUsrExist(newUsrData[2])) {

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
                statement = connection.createStatement();
                statement.executeUpdate(
                        "INSERT INTO user (firstName, lastName, email, password, vacationdays,hierarchy, groupid)"
                                + "VALUES ('" + newUsrData[0] + "', '" + newUsrData[1] + "', '" + newUsrData[2] + "', '"
                                + newUsrData[3] + "', 30, '" + newUsrData[4] + "', '" + newUsrData[5] + "')");
                resultSet = statement.executeQuery("SELECT id FROM user WHERE email='" + newUsrData[2] + "'");
                while (resultSet.next()) {
                    String tempId = resultSet.getString("id");
                    statement.executeUpdate("CREATE TABLE timetable_" + tempId + " (\n" + "id int(11) NOT NULL,\n"
                            + "userid int(11) DEFAULT NULL,\n" + "date varchar(128) NOT NULL,\n"
                            + "worktime varchar(128) NOT NULL,\n" + "start varchar(128) NOT NULL,\n"
                            + "end varchar(128) NOT NULL,\n" + "break varchar(128) NOT NULL,\n"
                            + "vacation int(11) NOT NULL,\n" + "illness int(11) NOT NULL\n" + ")");
                }
            } catch (SQLException ignore) {
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (statement != null) {
                    try {
                        statement.close();
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User was Added.");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This user does aleady exist!");
            alert.show();
        }

    }

    /**
     * This function takes in a E-Mail of {@code deleteUser} and removes the User
     * from the Database and the timetable.
     * 
     * @param deleteUser
     */
    public static void removeUser(String deleteUser) {
        if (doesUsrExist(deleteUser)) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT id FROM user WHERE email='" + deleteUser + "'");
                while (resultSet.next()) {
                    String tmpId = resultSet.getString("id");
                    statement.executeUpdate("DELETE FROM user WHERE user.id = " + tmpId);
                    statement.executeUpdate("DROP TABLE timetable_" + tmpId);
                }
            } catch (SQLException ignore) {
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (statement != null) {
                    try {
                        statement.close();
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User was Removed.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This does not exist!");
            alert.show();
        }
    }

    /**
     * This function takes in a {@code userEMail} and checks if the User is
     * existing.
     * 
     * @param usrEMail
     * @return true or False
     */
    private static boolean doesUsrExist(String usrEMail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt", "root", "");
            preparedStatement = connection.prepareStatement("SELECT id FROM user WHERE email='" + usrEMail + "'");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                preparedStatement.close();
                resultSet.close();
                connection.close();
                return false;
            } else {
                while (resultSet.next()) {
                    preparedStatement.close();
                    resultSet.close();
                    connection.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No connection to the Database!");
            alert.show();
        }
        return true;
    }

    // private static void enterStartWorktime() {
    // Connection connection = null;
    // PreparedStatement preparedStatement = null;
    // ResultSet resultSet = null;

    // try{
    // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swt",
    // "root", "");
    // preparedStatement = connection.prepareStatement("SELECT id FROM user WHERE
    // email='" + usrEMail + "'");

    // }
    // }

}
