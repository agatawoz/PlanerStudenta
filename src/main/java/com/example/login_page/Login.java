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
    public void userLogin(ActionEvent event)throws IOException{
        checkLogin(event);
    }

    private void checkLogin(ActionEvent event)throws IOException{
        MainPage m = new MainPage();

        if (username.getText().equals(USERNAME) && password.getText().equals(PASSWORD)){
            wrongLogin.setText("Poprawnie zalogowany!");
            //m.changeScene("mainPage.fxml");
            root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-30);
            String css = Main.class.getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        }else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogin.setText("Wpisz brakujące dane");
        }
        else{
            wrongLogin.setText("Nieprawidłowa nazwa użytkownika lub hasło!");
        }
    }
}
