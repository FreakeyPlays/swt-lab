package de.hse.swt.timemanagement;

import java.io.IOException;
import javafx.fxml.FXML;

public class mainController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}