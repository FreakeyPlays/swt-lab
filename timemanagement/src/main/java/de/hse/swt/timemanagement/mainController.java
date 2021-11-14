package de.hse.swt.timemanagement;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class mainController implements Initializable {

    @FXML
    private ComboBox<String> addUsrCombo;

    @FXML
    private Label activeVacationDaysTxt;

    @FXML
    private Label activeWorktimeTxt;

    @FXML
    private Label addUserMenuBtn;

    @FXML
    private Group addUserPane;

    @FXML
    private Button addUsrBtn;

    @FXML
    private TextField addUsrEMail;

    @FXML
    private TextField addUsrFirstName;

    @FXML
    private TextField addUsrGrpId;

    @FXML
    private TextField addUsrHier;

    @FXML
    private TextField addUsrLastName;

    @FXML
    private TextField addUsrPwd;

    @FXML
    private Label breakBtn;

    @FXML
    private TextField editWktBreakIn;

    @FXML
    private Label editWktDateTxt;

    @FXML
    private TextArea editWktNotesIn;

    @FXML
    private Button editWktSaveBtn;

    @FXML
    private TextField editWktTimeIn;

    @FXML
    private Group editWorkTimePane;

    @FXML
    private Label firstNameTxt;

    @FXML
    private Label flexTimeMenuBtn;

    @FXML
    private Label lastNameTxt;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField removeUserFirstName;

    @FXML
    private Button removeUsrBtn;

    @FXML
    private TextField removeUsrEMail;

    @FXML
    private TextField removeUsrLastName;

    @FXML
    private Label userInfoMenuBtn;

    @FXML
    private Label vacationMenuBtn;

    @FXML
    private Label wktBreakTxt;

    @FXML
    private DatePicker wktDatePicker;

    @FXML
    private Label wktDateTxt;

    @FXML
    private Button wktEditBtn;

    @FXML
    private Label wktNotesTxt;

    @FXML
    private Label wktTimeTxt;

    @FXML
    private Label workTimeMenuBtn;

    @FXML
    private Group workTimePane;

    @FXML
    private Label worktimeBtn;

    private static Label static_firstNameTxt;
    private static Label static_lastNameTxt;
    private static Label static_activeVacationDaysTxt;
    private static Label static_addUserMenuBtn;
    private static Label static_userInfoMenuBtn;
    private static Label static_wktTimeTxt;
    private static Label static_wktBreakTxt;

    private static boolean isWorking;
    private static boolean isOnBreak = false;
    private static LocalDate selectedDate;
   
    @FXML
    public void dateSelected() throws IOException {
        LocalDate currentMonth = LocalDate.now();
        LocalDate localeDate = wktDatePicker.getValue();
        selectedDate = localeDate;

        // Query if selected month/year equal to current month/year
        boolean monthQuery = currentMonth.getMonth() == selectedDate.getMonth();
        boolean yearQuery = currentMonth.getYear() == selectedDate.getYear();

        if (localeDate == null) {
            wktDateTxt.setText("No Date selected.");
            setTimes("00:00:00", "00:00:00");
            wktEditBtn.setDisable(true);
            wktEditBtn.setOpacity(0);
        } else {

            if (localeDate.isBefore(LocalDate.now().plusDays(1)) && monthQuery && yearQuery) {
                String[] date = localeDate.toString().split("-");
                String weekDayUppercase = localeDate.getDayOfWeek().toString().toLowerCase();
                String weekDay = weekDayUppercase.substring(0, 1).toUpperCase() + weekDayUppercase.substring(1);

                DBUtils.getDataOfDate(localeDate);

                wktDateTxt.setText(weekDay + ", " + date[2] + "." + date[1] + "." + date[0]);
                wktEditBtn.setDisable(false);
                wktEditBtn.setOpacity(1);
            } else {
                String[] date = localeDate.toString().split("-");
                String weekDayUppercase = localeDate.getDayOfWeek().toString().toLowerCase();
                String weekDay = weekDayUppercase.substring(0, 1).toUpperCase() + weekDayUppercase.substring(1);

                wktDateTxt.setText(weekDay + ", " + date[2] + "." + date[1] + "." + date[0]);
                wktEditBtn.setDisable(true);
                wktEditBtn.setOpacity(0);
            }

        }
    }

    @FXML
    private void editWorktime() {
        editWktDateTxt.setText(wktDateTxt.getText());

        String worktime = wktTimeTxt.getText();
        String breaktime = wktBreakTxt.getText();

        editWktTimeIn.setText(worktime);
        editWktBreakIn.setText(breaktime);

        workTimePane.setDisable(true);
        workTimePane.setOpacity(0);
        editWorkTimePane.setDisable(false);
        editWorkTimePane.setOpacity(1);
    }

    @FXML
    private void saveWorktime() {
        if (editWktTimeIn.getText().matches("\\d{2}h \\d{2}m \\d{2}s")
                && editWktBreakIn.getText().matches("\\d{2}h \\d{2}m \\d{2}s")) {
            String newWorkTime = editWktTimeIn.getText();
            String newBreakTime = editWktBreakIn.getText();
            DBUtils.compareData(selectedDate, codeTimeFormat(newWorkTime), codeTimeFormat(newBreakTime));
            wktTimeTxt.setText(newWorkTime);
            wktBreakTxt.setText(newBreakTime);
            editWorkTimePane.setDisable(true);
            editWorkTimePane.setOpacity(0);
            workTimePane.setDisable(false);
            workTimePane.setOpacity(1);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("The Times must be in format: 00h 00m 00s");
            alert.show();
        }
    }

    @FXML
    private void trackWorktime() {
        if (isWorking) {
            if (isOnBreak) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You are still on a Break!");
                alert.show();
            } else {
                isWorking = false;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do yout really want to End your Worktime?",
                        ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    worktimeBtn.setText("Start Worktime");
                }
            }
        } else {
            isWorking = true;
            worktimeBtn.setText("End Worktime");
        }
    }

    @FXML
    private void trackBreaktime() {
        if (isWorking) {
            if (isOnBreak) {
                isOnBreak = false;
                breakBtn.setText("Start Break");
            } else {
                isOnBreak = true;
                breakBtn.setText("End Break");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are not Working!");
            alert.show();
        }
    }

    @FXML
    private void addUser() {

        if (addUsrFirstName.getText().isEmpty() || addUsrLastName.getText().isEmpty() || addUsrEMail.getText().isEmpty()
                || addUsrPwd.getText().isEmpty() || addUsrGrpId.getText().isEmpty() || addUsrCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter all Fields for Add User!");
            alert.show();
        } else {
            if (!addUsrFirstName.getText().matches("\\w+") || !addUsrLastName.getText().matches("\\w+")
                    || !addUsrEMail.getText().matches("^(.+)@(.+)\\.[a-zA-Z]{2,}")
                    || !addUsrPwd.getText().matches(".{5,}") || !addUsrGrpId.getText().matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please check your input!");
                alert.show();
            } else {
                String[] newUserArr = new String[6];
                newUserArr[0] = addUsrFirstName.getText();
                newUserArr[1] = addUsrLastName.getText();
                newUserArr[2] = addUsrEMail.getText();
                newUserArr[3] = addUsrPwd.getText();
                newUserArr[4] = addUsrCombo.getValue();
                newUserArr[5] = addUsrGrpId.getText();

                DBUtils.addUserToDB(newUserArr);

                addUsrFirstName.clear();
                addUsrLastName.clear();
                addUsrEMail.clear();
                addUsrPwd.clear();
                addUsrCombo.valueProperty().set(null);
                addUsrGrpId.clear();
            }
        }

    }

    @FXML
    private void removeUser() {
        if (removeUserFirstName.getText().isEmpty() || removeUsrLastName.getText().isEmpty()
                || removeUsrEMail.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter all Fields for Remove User!");
            alert.show();
        } else {
            if (!removeUserFirstName.getText().matches("\\w+") || !removeUsrLastName.getText().matches("\\w+")
                    || !removeUsrEMail.getText().matches("^(.+)@(.+)\\.[a-zA-Z]{2,}")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please check your input!");
                alert.show();
            } else {
                DBUtils.removeUser(removeUsrEMail.getText().toString());

                removeUserFirstName.clear();
                removeUsrLastName.clear();
                removeUsrEMail.clear();
            }
        }
    }

    @FXML
    private void logout() throws IOException {
        if (isWorking) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are still tracking your Workingtime!");
            alert.show();
        } else {
            DBUtils.usr.clearData();
            App.setRoot("login", 900, 600);
        }
    }

    @FXML
    private void exit() {
        if (isWorking) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are still tracking your Workingtime!");
            alert.show();
        } else {
            DBUtils.usr.clearData();
            App.exit();
        }
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

    @FXML
    private void showWorktime() {
        if (editWorkTimePane.isDisabled()) {
            disableAllPanes();

            workTimeMenuBtn.getStyleClass().add("active");
            workTimePane.setDisable(false);
            workTimePane.setOpacity(1);
        }
    }

    @FXML
    private void showFlexTime() {
        if (editWorkTimePane.isDisable()) {
            disableAllPanes();

            flexTimeMenuBtn.getStyleClass().add("active");
        }
    }

    @FXML
    private void showVacation() {
        if (editWorkTimePane.isDisable()) {
            disableAllPanes();

            vacationMenuBtn.getStyleClass().add("active");
        }
    }

    @FXML
    private void showAddUser() {
        if (editWorkTimePane.isDisable()) {
            disableAllPanes();

            addUserMenuBtn.getStyleClass().add("active");
            addUserPane.setDisable(false);
            addUserPane.setOpacity(1);
        }
    }

    @FXML
    private void showUserInfo() {
        if (editWorkTimePane.isDisable()) {
            disableAllPanes();

            userInfoMenuBtn.getStyleClass().add("active");
        }
    }

    public void disableAllPanes() {
        workTimeMenuBtn.getStyleClass().remove("active");
        workTimePane.setDisable(true);
        workTimePane.setOpacity(0);
        addUserMenuBtn.getStyleClass().remove("active");
        addUserPane.setDisable(true);
        addUserPane.setOpacity(0);
        flexTimeMenuBtn.getStyleClass().remove("active");
        vacationMenuBtn.getStyleClass().remove("active");
        userInfoMenuBtn.getStyleClass().remove("active");
    }

    public static void showSupervisorMenu() {
        static_addUserMenuBtn.setDisable(false);
        static_addUserMenuBtn.setOpacity(1);
        static_userInfoMenuBtn.setDisable(false);
        static_userInfoMenuBtn.setOpacity(1);
    }

    public static void setNames(String firstName, String lastName, int vacDays) {
        static_firstNameTxt.setText(firstName);
        static_lastNameTxt.setText(lastName);
        static_activeVacationDaysTxt.setText(vacDays + " Day's");
    }

    public static void setTimes(String worktime, String breaktime) {
        static_wktTimeTxt.setText(displayTimeFormat(worktime));
        static_wktBreakTxt.setText(displayTimeFormat(breaktime));
    }

    private static String displayTimeFormat(String time) {
        String[] arr = time.split(":");
        String formatted = arr[0] + "h " + arr[1] + "m " + arr[2] + "s";
        return formatted;
    }

    private static String codeTimeFormat(String time) {
        String[] arr = time.split("[hms][ ]?");
        String formatted = arr[0] + ":" + arr[1] + ":" + arr[2];
        return formatted;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUsrCombo.getItems().addAll("Employee", "Supervisor");

        static_firstNameTxt = firstNameTxt;
        static_lastNameTxt = lastNameTxt;
        static_activeVacationDaysTxt = activeVacationDaysTxt;
        static_addUserMenuBtn = addUserMenuBtn;
        static_userInfoMenuBtn = userInfoMenuBtn;
        static_wktTimeTxt = wktTimeTxt;
        static_wktBreakTxt = wktBreakTxt;

        App.textFieldValidation(addUsrFirstName, "\\w+");
        App.textFieldValidation(addUsrLastName, "\\w+");
        App.textFieldValidation(addUsrEMail, "^(.+)@(.+)\\.[a-zA-Z]{2,}");
        App.textFieldValidation(addUsrPwd, ".{5,}");
        // dropdown Validation
        App.textFieldValidation(addUsrGrpId, "\\d+");
        App.textFieldValidation(removeUserFirstName, "\\w+");
        App.textFieldValidation(removeUsrLastName, "\\w+");
        App.textFieldValidation(removeUsrEMail, "^(.+)@(.+)\\.[a-zA-Z]{2,}");
        App.textFieldValidation(editWktTimeIn, "\\d{2}h\\s\\d{2}m\\s\\d{2}s");
        App.textFieldValidation(editWktBreakIn, "\\d{2}h\\s\\d{2}m\\s\\d{2}s");
        
    }

}
