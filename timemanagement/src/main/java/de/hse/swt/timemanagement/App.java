package de.hse.swt.timemanagement;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage mainStage;
    private static double xOffset = 0;
    private static double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        Parent root = loadFXML("login");
        scene = new Scene(root);
        mainStage.setTitle("Time Managment System");
        mainStage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/de/hse/swt/timemanagement/img/TMS-Logo.png")));

        makeDraggable(root);

        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void makeDraggable(Parent root) {
        // Move the Login Screen
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mainStage.setX(e.getScreenX() - xOffset);
                mainStage.setY(e.getScreenY() - yOffset);
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        makeDraggable(root);
        mainStage.setWidth(1280);
        mainStage.setHeight(720);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void minimize() {
        mainStage.setIconified(true);
    }

    public static void main(String[] args) {
        launch();
    }

}