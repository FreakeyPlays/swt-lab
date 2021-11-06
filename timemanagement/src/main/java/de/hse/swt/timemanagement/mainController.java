package de.hse.swt.timemanagement;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class mainController {

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
    private DatePicker editWktDatePicker;

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
    private Label vacationBtn1;

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

    @FXML
    public void dateSelected() {
        LocalDate localeDate = wktDatePicker.getValue();

        if (localeDate == null) {
            wktDateTxt.setText("No Date selected.");
            wktEditBtn.setDisable(true);
            wktEditBtn.setOpacity(0);
        } else {
            Instant instant = Instant.from(localeDate.atStartOfDay(ZoneId.systemDefault()));
            String date = Date.from(instant).toString();
            String[] dateArr = date.split(" ");
            String[] resultDate = new String[4];

            switch (dateArr[0]) {
            case "Mon":
                resultDate[0] = "Monday, ";
                break;
            case "Tue":
                resultDate[0] = "Tuesday, ";
                break;
            case "Wed":
                resultDate[0] = "Wednesday, ";
                break;
            case "Thu":
                resultDate[0] = "Thursday, ";
                break;
            case "Fri":
                resultDate[0] = "Friday, ";
                break;
            case "Sat":
                resultDate[0] = "Saturday, ";
                break;
            case "Sun":
                resultDate[0] = "Sunday, ";
                break;
            }

            resultDate[1] = dateArr[2];

            switch (dateArr[1]) {
            case "Jan":
                resultDate[2] = ".01.";
                break;
            case "Feb":
                resultDate[2] = ".02.";
                break;
            case "Mar":
                resultDate[2] = ".03.";
                break;
            case "Apr":
                resultDate[2] = ".04.";
                break;
            case "May":
                resultDate[2] = ".05.";
                break;
            case "Jun":
                resultDate[2] = ".06.";
                break;
            case "Jul":
                resultDate[2] = ".07.";
                break;
            case "Aug":
                resultDate[2] = ".08.";
                break;
            case "Sep":
                resultDate[2] = ".09.";
                break;
            case "Oct":
                resultDate[2] = ".10.";
                break;
            case "Nov":
                resultDate[2] = ".11.";
                break;
            case "Dez":
                resultDate[2] = ".12.";
                break;
            }

            resultDate[3] = dateArr[5];

            wktDateTxt.setText(resultDate[0] + resultDate[1] + resultDate[2] + resultDate[3]);
            wktEditBtn.setDisable(false);
            wktEditBtn.setOpacity(1);
        }
    }

    @FXML
    private void editWorktime() {
        LocalDate localeDate = wktDatePicker.getValue();
        if (localeDate == null) {
            System.out.println("No Date selected!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There was no Date selected.");
            alert.show();
        } else {
            editWktDateTxt.setText(wktDateTxt.getText());
            workTimePane.setDisable(true);
            workTimePane.setOpacity(0);
            editWorkTimePane.setDisable(false);
            editWorkTimePane.setOpacity(1);
        }
    }

    @FXML
    private void saveWorktime() {
        editWorkTimePane.setDisable(true);
        editWorkTimePane.setOpacity(0);
        workTimePane.setDisable(false);
        workTimePane.setOpacity(1);
    }

    @FXML
    private void exitAndLogout(ActionEvent e) {
        App.exit();
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

}
