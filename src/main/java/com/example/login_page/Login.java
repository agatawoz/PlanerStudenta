package com.example.login_page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    public Login(){

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void userLogin()throws IOException{
        checkLogin();
    }

    public void keyLogin(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            checkLogin();
        }
    }

    private void checkLogin()throws IOException{
        String userLogin = username.getText();
        String userPassword = password.getText();
        boolean dbResult = DatabaseManager.checkLoginData(userLogin, userPassword);
        if (dbResult){
            wrongLogin.setText("Poprawnie zalogowany!");


            root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
            stage = (Stage) (button.getScene().getWindow());
            stage.setMaximized(true);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-20);
            //MainPage m = new MainPage();
            MainPage.changeScene(root, stage, scene);


        }else if(userLogin.isEmpty() && userPassword.isEmpty()) {
            wrongLogin.setText("Wpisz brakujące dane");
        }
        else{
            wrongLogin.setText("Nieprawidłowa nazwa użytkownika lub hasło!");
        }
    }
}
