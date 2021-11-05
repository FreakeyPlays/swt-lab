package de.hse.swt.timemanagement;

import java.io.IOException;

import javafx.event.ActionEvent;
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
    private Label editWktDate;

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
    private void exitAndLogout(ActionEvent e) {
        App.exit();
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

}
