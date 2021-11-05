package de.hse.swt.timemanagement;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField usrInput;

    @FXML
    private PasswordField pwdInput;

    @FXML
    private void exit(ActionEvent e) {
        App.exit();
    }

    @FXML
    private void minimize() {
        App.minimize();
    }

    @FXML
    private void login() throws IOException {
        DBUtils.logInUser(usrInput.getText(), pwdInput.getText());
    }
}
