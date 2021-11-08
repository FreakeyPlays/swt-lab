package de.hse.swt.timemanagement;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
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
        scene.setFill(Color.TRANSPARENT);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/de/hse/swt/timemanagement/img/TMS-Logo.png")));

        makeDraggable(root);

        mainStage.setScene(scene);
        mainStage.show();
        centerWindow();
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

    public static void centerWindow() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainStage.setX((primScreenBounds.getWidth() - mainStage.getWidth()) / 2);
        mainStage.setY((primScreenBounds.getHeight() - mainStage.getHeight()) / 2);
    }

    static void setRoot(String fxml, int witdh, int height) throws IOException {
        mainStage.close();
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        makeDraggable(root);
        mainStage.setWidth(witdh);
        mainStage.setHeight(height);
        centerWindow();
        mainStage.show();
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