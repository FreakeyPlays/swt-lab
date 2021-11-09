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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class mainController implements Initializable {

    @FXML
    private Label activeVacationDaysTxt;

    @FXML
    private Label activeWorktimeTxt;

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
    private Label flexTimeBtn;

    @FXML
    private Label flexTimeBtn1;

    @FXML
    private Label lastNameTxt;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label vacationBtn;

    @FXML
    private Label employeeInteract;

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
    private Group workTimePane;

    @FXML
    private Label worktimeBtn;

    private static Label static_firstNameTxt;
    private static Label static_lastNameTxt;
    private static Label static_activeVacationDaysTxt;
    private static Label static_employeeInteract;
    private static Label static_wktTimeTxt;
    private static Label static_wktBreakTxt;

    private static boolean isWorking;
    private static boolean isOnBreak = false;
    private static LocalDate selectedDate;

    @FXML
    public void dateSelected() throws IOException {
        LocalDate localeDate = wktDatePicker.getValue();
        selectedDate = localeDate;

        if (localeDate == null) {
            wktDateTxt.setText("No Date selected.");
            setTimes("00:00:00", "00:00:00");
            wktEditBtn.setDisable(true);
            wktEditBtn.setOpacity(0);
        } else {

            if (localeDate.isBefore(LocalDate.now().plusDays(1))) {
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
        String newWorkTime = editWktTimeIn.getText();
        String newBreakTime = editWktBreakIn.getText();
        DBUtils.compareData(selectedDate, codeTimeFormat(newWorkTime), codeTimeFormat(newBreakTime));
        editWorkTimePane.setDisable(true);
        editWorkTimePane.setOpacity(0);
        workTimePane.setDisable(false);
        workTimePane.setOpacity(1);
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
    private void logout() throws IOException {
        if (isWorking) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are still tracking your Workingtime!");
            alert.show();
        } else {
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
            App.exit();
        }
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

    public static void showEmployeeInteract() {
        static_employeeInteract.setDisable(false);
        static_employeeInteract.setOpacity(1);
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
        static_firstNameTxt = firstNameTxt;
        static_lastNameTxt = lastNameTxt;
        static_activeVacationDaysTxt = activeVacationDaysTxt;
        static_employeeInteract = employeeInteract;
        static_wktTimeTxt = wktTimeTxt;
        static_wktBreakTxt = wktBreakTxt;
    }

}
