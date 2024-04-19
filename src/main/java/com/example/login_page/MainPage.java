package com.example.login_page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isCalendarShown = false;

    @FXML
    private Button dayButton;
    @FXML
    private VBox planSection;
    @FXML
    private VBox importantSection;
    @FXML
    private StackPane calendarSection;
    @FXML
    private Pane calendarBackground;

    public void changeScene(Parent root, Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-30);
        String css = Main.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void showCalendar(){
        if (isCalendarShown==false){
            dayButton.setStyle("-fx-background-color: #303030;");
            dayButton.setOnMouseEntered(event -> dayButton.setStyle("-fx-background-color: #303030;"));
            dayButton.setOnMouseExited(event -> dayButton.setStyle("-fx-background-color: #303030;"));
            planSection.setVisible(false);
            importantSection.setVisible(false);
            calendarSection.setVisible(true);
            calendarBackground.setVisible(true);
            isCalendarShown = true;
        }
        else {
            dayButton.setStyle("-fx-background-color: #404040;");
            dayButton.setOnMouseEntered(event -> dayButton.setStyle("-fx-background-color: #404040;"));
            dayButton.setOnMouseExited(event -> dayButton.setStyle("-fx-background-color: #444444;"));
            calendarSection.setVisible(false);
            planSection.setVisible(true);
            importantSection.setVisible(true);
            calendarBackground.setVisible(false);
            isCalendarShown = false;
        }
    }
}
