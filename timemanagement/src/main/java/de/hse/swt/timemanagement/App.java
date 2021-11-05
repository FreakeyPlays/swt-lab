package de.hse.swt.timemanagement;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage mainStage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        Parent root = loadFXML("login");
        scene = new Scene(root);
        mainStage.setTitle("Time Managment System");
        mainStage.initStyle(StageStyle.UNDECORATED);

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

        // Show the Scene
        mainStage.setScene(scene);
        mainStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        mainStage.setWidth(1280);
        mainStage.setHeight(720);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}