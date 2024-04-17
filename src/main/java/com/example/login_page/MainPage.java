package com.example.login_page;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void changeScene(Parent root, Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-30);
        String css = Main.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
