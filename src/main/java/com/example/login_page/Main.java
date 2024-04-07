package com.example.login_page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 600, 480);
        String css = Main.class.getResource("style.css").toExternalForm();
        loginScene.getStylesheets().add(css);
        stage.setTitle("Planer studenta");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}