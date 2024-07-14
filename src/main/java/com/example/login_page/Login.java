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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    @FXML
    private GridPane loadGridPane;
    @FXML
    private ImageView imageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane oldGridPane;
    @FXML
    private AnchorPane newGridPane;
    @FXML
    private GridPane oldButtonsGridPane;
    @FXML
    private GridPane newButtonsGridPane;
    @FXML
    private Button oldButton;
    @FXML
    private Button newButton;
    @FXML
    private Button nButton;
    @FXML
    private TextField username1;
    @FXML
    private TextField password1;
    @FXML
    private Label wrongLogin1;

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
        imageView.fitWidthProperty().bind(borderPane.widthProperty());
        imageView.fitHeightProperty().bind(borderPane.heightProperty());
        String userLogin = username.getText();
        String userPassword = password.getText();
        if(userLogin.isEmpty() || userPassword.isEmpty()) {
            wrongLogin.setText("Wpisz brakujące dane");
            wrongLogin.setVisible(true);
            return;
        }
        int dbResult = DatabaseManager.checkLoginData(userLogin, userPassword);
        if (dbResult != 0){
            loadGridPane.setVisible(true);
            wrongLogin.setText("Poprawnie zalogowany!");
            wrongLogin.setVisible(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
            root = loader.load();
            stage = (Stage) (button.getScene().getWindow());
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-20);
            stage.setMaximized(true);
            changeScene(root, stage, scene, 'm');
            MainPage mainPage = loader.getController();
            mainPage.setCurrentUserID(dbResult);
            mainPage.updateSubjects(null);
            mainPage.updateAllSubjects();
            mainPage.createCalendar();
            mainPage.changeLoginLabel(userLogin);
            mainPage.editComboBoxes();
            mainPage.addHandler(root);


        }
        else{
            wrongLogin.setText("Nieprawidłowa nazwa użytkownika lub hasło!");
            wrongLogin.setVisible(true);
        }
    }

    public void changeScene(Parent root, Stage stage, Scene scene, char type) {
        String css = Main.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
//        stage.setMaximized(type == 'm');

        stage.show();
    }

    public void showNewGridPane(ActionEvent event){
        if (event.getTarget() == oldButton){
            oldGridPane.setVisible(true);
            oldButtonsGridPane.setVisible(true);
            newGridPane.setVisible(false);
            newButtonsGridPane.setVisible(false);
        }
        else{
            oldGridPane.setVisible(false);
            oldButtonsGridPane.setVisible(false);
            newGridPane.setVisible(true);
            newButtonsGridPane.setVisible(true);
        }
    }

    public void createAccount() throws IOException {
        System.out.println("hello");
        String userLogin = username1.getText();
        String userPassword = password1.getText();
        if(userLogin.isEmpty() || userPassword.isEmpty()) {
            wrongLogin1.setText("Wpisz brakujące dane");
            wrongLogin1.setVisible(true);
            return;
        }


        int dbResult = DatabaseManager.checkNew(userLogin, userPassword);
        if (dbResult > 0){
            loadGridPane.setVisible(true);
            wrongLogin1.setText("Poprawnie zalogowany!");
            wrongLogin1.setVisible(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
            root = loader.load();
            stage = (Stage) (button.getScene().getWindow());
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-20);
            stage.setMaximized(true);
            changeScene(root, stage, scene, 'm');
            MainPage mainPage = loader.getController();
            mainPage.setCurrentUserID(dbResult);
            mainPage.updateSubjects(null);
            mainPage.updateAllSubjects();
            mainPage.createCalendar();
            mainPage.changeLoginLabel(userLogin);
            mainPage.editComboBoxes();
            mainPage.addHandler(root);


        }
        else{
            System.out.println(dbResult);
            wrongLogin1.setText("Konto o podanej nazwie już istnieje");
            wrongLogin1.setVisible(true);
        }
    }

}
