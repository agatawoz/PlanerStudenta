package com.example.login_page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPage {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static boolean isCalendarShown = false;

    private ArrayList<GridPane> gridPanes;

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
    @FXML
    private Button logoutButton;

    @FXML
    private GridPane subject0;
    @FXML
    private Label subject0_startTime;
    @FXML
    private Label subject0_endTime;
    @FXML
    private Label subject0_name;
    @FXML
    private Label subject0_type;
    @FXML
    private Label subject0_place;



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
            dayButton.setStyle("-fx-background-color: #444444;");
            dayButton.setOnMouseEntered(event -> dayButton.setStyle("-fx-background-color: #404040;"));
            dayButton.setOnMouseExited(event -> dayButton.setStyle("-fx-background-color: #444444;"));
            calendarSection.setVisible(false);
            planSection.setVisible(true);
            importantSection.setVisible(true);
            calendarBackground.setVisible(false);
            isCalendarShown = false;
        }
    }

    public void logout () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        root = loader.load();
        stage = (Stage) (logoutButton.getScene().getWindow());
        stage.setMaximized(false);
        Scene scene = new Scene(root, 800, 500);
        Login login = loader.getController();
        login.changeScene(root, stage, scene);
    }

    boolean updateSubjects(String userLogin, LocalDate date){
        if (date == null) date = LocalDate.now();
        DatabaseManager.getSubjectsInfo(this, userLogin, date);
       // subject0.setVisible(false);
       // subject1.setVisible(false);
       // subject2.setVisible(false);
       // subject3.setVisible(false);
       // subject4.setVisible(false);
        //subject5.setVisible(false);
        //subject6.setVisible(false);
        return (true);
    }

    void showSubjects (List<SubjectEvent> subjectsEvents) {
//      remove all elements of ArrayList (and their children)
        for (int i = 0; i < subjectsEvents.size(); i++) {
            SubjectEvent subjectEvent = subjectsEvents.get(i);
//            TODO
//            create GridPane (subject/event block)
//            customise GridPane
//            add Labels, customise them
//            get Strings/LocalTime from subjectEvent (using getters), set Labels text
//            add Labels to GridPane
//            add GridPane to ArrayList
        }
//            add all elements of ArrayList as children of VBox
    }
}
