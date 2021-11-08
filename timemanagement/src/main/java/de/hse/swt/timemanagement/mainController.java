package de.hse.swt.timemanagement;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Group;
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
            String[] date = localeDate.toString().split("-");
            String weekDayUppercase = localeDate.getDayOfWeek().toString().toLowerCase();
            String weekDay = weekDayUppercase.substring(0, 1).toUpperCase() + weekDayUppercase.substring(1);

            wktDateTxt.setText(weekDay + ", " + date[2] + "." + date[1] + "." + date[0]);
            wktEditBtn.setDisable(false);
            wktEditBtn.setOpacity(1);
        }
    }

    @FXML
    private void editWorktime() {
        editWktDateTxt.setText(wktDateTxt.getText());
        workTimePane.setDisable(true);
        workTimePane.setOpacity(0);
        editWorkTimePane.setDisable(false);
        editWorkTimePane.setOpacity(1);
    }

    @FXML
    private void saveWorktime() {
        editWorkTimePane.setDisable(true);
        editWorkTimePane.setOpacity(0);
        workTimePane.setDisable(false);
        workTimePane.setOpacity(1);
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login", 900, 600);
    }

    @FXML
    private void exit() {
        App.exit();
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

}
