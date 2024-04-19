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

    private final String USERNAME = "agata";
    private final String PASSWORD = "123";

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

        if (username.getText().equals(USERNAME) && password.getText().equals(PASSWORD)){
            wrongLogin.setText("Poprawnie zalogowany!");
            root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
            stage = (Stage) (button.getScene().getWindow());
            MainPage m = new MainPage();
            m.changeScene(root, stage);


        }else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogin.setText("Wpisz brakujące dane");
        }
        else{
            wrongLogin.setText("Nieprawidłowa nazwa użytkownika lub hasło!");
        }
    }
}
