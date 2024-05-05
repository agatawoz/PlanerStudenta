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
        //FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        //Parent root = loginLoader.load();
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Scene loginScene = new Scene(root, 600, 480);
        stage.setTitle("Planer studenta");
        //stage.setScene(loginScene);
        //stage.show();
        MainPage.changeScene(root, stage, loginScene);
    }

    public static void main(String[] args) {
        DatabaseManager.createDBConnection();
        launch();
    }
}