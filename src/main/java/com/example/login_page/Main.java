package com.example.login_page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Parent root = loader.load();
        Scene loginScene = new Scene(root, 800, 500);
        stage.setTitle("Planer studenta");
        Login login = loader.getController();
        login.changeScene(root, stage, loginScene);
    }

    public static void main(String[] args) {
        DatabaseManager.createDBConnection();
        launch();
    }
}