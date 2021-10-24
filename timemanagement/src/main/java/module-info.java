module de.hse.swt.timemanagement {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens de.hse.swt.timemanagement to javafx.fxml;
    exports de.hse.swt.timemanagement;
}
