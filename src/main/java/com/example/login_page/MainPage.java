package com.example.login_page;

import com.calendarfx.view.YearMonthView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class MainPage {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static boolean isCalendarShown = false;

    private int currentUserID;


    @FXML
    private Button dayButton;
    @FXML
    private Button detailsButton;
    @FXML
    private VBox planSection;
    @FXML
    private StackPane calendarSection;
    @FXML
    private Pane calendarBackground;
    @FXML
    private Button logoutButton;
    @FXML
    private VBox seVBox;
    @FXML
    private VBox instructorVBox;
    @FXML
    private GridPane gradesGridPane;
    @FXML
    private VBox notesVBox;
    @FXML
    private Label instructorName;
    @FXML
    private Label instructorMail;
    @FXML
    private Label instructorRoom;
    @FXML
    private TextArea notesTextArea;

    @FXML
    private Pane allSubjectsBackground;
    @FXML
    private GridPane subjectsControlGridPane;
    @FXML
    private GridPane allSubjectsGridPane;
    @FXML
    private GridPane editWindowGridPane;
    @FXML
    private VBox gradesVBox;
    @FXML
    private Label averageLabel;
    @FXML
    private Label allAverageLabel;
    @FXML
    private VBox allSubjectsVBox;
    @FXML
    private ComboBox<String> createSubjectComboBox;
    @FXML
    private ComboBox<String> subjectTypeComboBox;
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private TextField instructorNameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private TextField roomTextField;
    @FXML
    private VBox editDateVBox;
    @FXML
    private VBox editGradeVBox;

    @FXML
    private Button saveNotesButton;
    @FXML
    private Label saveNotesLabel;
    @FXML
    private Button saveNameButton;
    @FXML
    private Button saveInstructorButton;
    @FXML
    private Button saveMailButton;
    @FXML
    private Button saveRoomButton;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private Label addDateLabel;
    @FXML
    private TextField gradeNameTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private Label addGradeLabel;
    @FXML
    private TextField addSubjectTextField;
    @FXML
    private Label addSubjectLabel;
    @FXML
    private ScrollPane allSubjectsScrollPane;
    @FXML
    private Pane eventsBackground;
    @FXML
    private GridPane eventsWindow;
    @FXML
    private TextField eventDateTextField;
    @FXML
    private TextField eventTimeTextField;
    @FXML
    private TextField eventInfoTextField;
    @FXML
    private Label eventLabel;
    @FXML
    private VBox eventsVBox;
    @FXML
    private Button manageSubjectsButton;
    @FXML
    private Button manageEventsButton;
    @FXML
    private GridPane logoutGridPane;
    @FXML
    private Label usernameLabel;
    private LocalDate currentDate;
    private int currentSubjectID;
    private int editSubjectID;

    public void setCurrentUserID(int currentUserID){
        this.currentUserID = currentUserID;
    }

public void createCalendar(){
    YearMonthView yearMonthView = new YearMonthView();
    yearMonthView.setMaxSize(400,400);
    currentDate = LocalDate.now();
    yearMonthView.getSelectedDates().add(currentDate);
    changeDateLabel(currentDate);
    yearMonthView.setShowTodayButton(false);
    yearMonthView.setShowWeekNumbers(false);
    yearMonthView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        System.out.println("Clicked on "+event.getTarget().toString());
        System.out.println("Current month: "+yearMonthView.getDate().toString());
        if (event.getTarget() instanceof YearMonthView.DateCell dateCell)  {
            LocalDate clickedDate = dateCell.getDate();
            if (currentDate.getMonth()!=clickedDate.getMonth()){
               clickedDate = yearMonthView.getDate();
            }
            currentDate = clickedDate;
            System.out.println("Date clicked: " + clickedDate);
            changeDateLabel(clickedDate);
            updateSubjects(clickedDate);
            showCalendar();
        }
    });

    calendarSection.getChildren().add(yearMonthView);
}

public void changeDateLabel(LocalDate date){
    Locale polishLocale = Locale.of("pl", "PL");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", polishLocale);
    String dateLabel = date.format(formatter);
    dateLabel = dateLabel.substring(0, 1).toUpperCase() + dateLabel.substring(1);
    dayButton.setText(dateLabel);
}

    public void showCalendar(){
        if (isCalendarShown==false){
            dayButton.setStyle("-fx-background-color: rgba(135, 77, 124, 0.2);");
            dayButton.setOnMouseEntered(event -> dayButton.setStyle("-fx-background-color: rgba(135, 77, 124, 0.2)"));
            dayButton.setOnMouseExited(event -> dayButton.setStyle("-fx-background-color: rgba(135, 77, 124, 0.2)"));
            planSection.setVisible(false);
            manageSubjectsButton.setVisible(false);
            manageEventsButton.setVisible(false);
            calendarSection.setVisible(true);
            calendarBackground.setVisible(true);
            isCalendarShown = true;
        }
        else {
            dayButton.setStyle("-fx-background-color: rgba(235, 160, 182, 0.1)");
            dayButton.setOnMouseEntered(event -> dayButton.setStyle("-fx-background-color: rgba(235, 160, 182, 0.2)"));
            dayButton.setOnMouseExited(event -> dayButton.setStyle("-fx-background-color: rgba(235, 160, 182, 0.1)"));
            calendarSection.setVisible(false);
            planSection.setVisible(true);
            manageSubjectsButton.setVisible(true);
            manageEventsButton.setVisible(true);
            calendarBackground.setVisible(false);
            isCalendarShown = false;
        }
    }

    public void changeLoginLabel(String userLogin){
        usernameLabel.setText(userLogin);
        logoutButton.setText(userLogin.toUpperCase());
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

    boolean updateSubjects(LocalDate date){
        detailsButton.setText("Szczegóły przedmiotu");
        instructorVBox.setVisible(false);
        gradesGridPane.setVisible(false);
        notesVBox.setVisible(false);
        if (date == null) date = LocalDate.now();
        DatabaseManager.getSubjectsInfo(this, currentUserID, date);
        return (true);
    }

    void showSubjects (List<SubjectEvent> subjectsEvents) {
//      remove all elements of ArrayList (and their children)
        seVBox.getChildren().clear();

        System.out.println("Subjects and events info: ");
        for (SubjectEvent subjectEvent : subjectsEvents) {
            if (subjectEvent.getType() == 's') {
                GridPane gridPane = new GridPane();
                gridPane.setMinHeight(30);
                gridPane.setMaxWidth(Double.MAX_VALUE);

                ColumnConstraints col1 = new ColumnConstraints(20, 20, Double.MAX_VALUE);
                col1.setHgrow(Priority.SOMETIMES);
                ColumnConstraints col2 = new ColumnConstraints(200, 200, Double.MAX_VALUE);
                col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
                ColumnConstraints col3 = new ColumnConstraints(20, 20, Double.MAX_VALUE);
                col3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
                ColumnConstraints col4 = new ColumnConstraints(50, 50, Double.MAX_VALUE);
                col4.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
                ColumnConstraints col5 = new ColumnConstraints(50, 50, Double.MAX_VALUE);
                col5.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
                gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);


                RowConstraints row1 = new RowConstraints(30, 30, Double.MAX_VALUE);
                row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                gridPane.getRowConstraints().add(row1);


                Label startTimeLabel = new Label(subjectEvent.getStartTime());
                Label nameLabel = new Label(subjectEvent.getName());
                Label endTimeLabel = new Label(subjectEvent.getEndTime());
                Label subjectTypeLabel = new Label(subjectEvent.getSubjectType());
                Label placeLabel = new Label(subjectEvent.getPlace());


                gridPane.add(startTimeLabel, 0, 0);
                gridPane.add(nameLabel, 1, 0);
                gridPane.add(endTimeLabel, 2, 0);
                gridPane.add(subjectTypeLabel, 3, 0);
                gridPane.add(placeLabel, 4, 0);
                gridPane.setId("subjectList");

                GridPane.setHalignment(startTimeLabel, HPos.CENTER);
                GridPane.setHalignment(nameLabel, HPos.CENTER);
                GridPane.setHalignment(endTimeLabel, HPos.CENTER);
                GridPane.setHalignment(subjectTypeLabel, HPos.CENTER);
                GridPane.setHalignment(placeLabel, HPos.CENTER);
                VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));

                gridPane.setOnMouseClicked(mouseEvent -> {
                            currentSubjectID = subjectEvent.getIdSubEve();
                            DatabaseManager.getInstructorInfo(this, currentUserID, subjectEvent.getIdSubEve());
                DatabaseManager.getGradesInfo(this, "mainPage", currentUserID, subjectEvent.getIdSubEve());
                });
                seVBox.getChildren().add(gridPane);
            }
            else {
                GridPane gridPane = new GridPane();
                gridPane.setMinHeight(30);
                gridPane.setMaxWidth(Double.MAX_VALUE);

                ColumnConstraints col6 = new ColumnConstraints(20, 20, 50);
                col6.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
                ColumnConstraints col7 = new ColumnConstraints(200, 200, Double.MAX_VALUE);
                col7.setHgrow(javafx.scene.layout.Priority.SOMETIMES);

                gridPane.getColumnConstraints().addAll(col6, col7);

                RowConstraints row2 = new RowConstraints(30, 30, Double.MAX_VALUE);
                row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                gridPane.getRowConstraints().add(row2);

                Label timeLabel3 = new Label(subjectEvent.getStartTime());
                Label eventLabel = new Label(subjectEvent.getPlace());
                GridPane.setHalignment(timeLabel3, HPos.CENTER);

                gridPane.add(timeLabel3, 0, 0);
                gridPane.add(eventLabel, 1, 0, GridPane.REMAINING, 1);
                gridPane.setId("eventList");

                VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));

                seVBox.getChildren().add( gridPane);
            }
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

    void updateInstructorInfo(String subjectName, String name, String mail, String room){
        detailsButton.setText("Szczegóły przedmiotu: \n"+subjectName);
        instructorName.setText("Prowadzący: "+name);
        instructorMail.setText("Mail: "+mail);
        instructorRoom.setText("Pokój: "+room);
        instructorVBox.setVisible(true);
        gradesGridPane.setVisible(true); //przenieść do innej metody
        notesVBox.setVisible(true); //
        saveNotesLabel.setVisible(false);
    }

    void updateNotes(String notes){
        notesTextArea.setText(notes);
    }

    public void showAllSubjectsSection(){
        if (!allSubjectsBackground.isVisible()){
            allSubjectsBackground.setVisible(true);
            subjectsControlGridPane.setVisible(true);
            addSubjectLabel.setVisible(false);
            DatabaseManager.getAllSubjectsInfo(this, currentUserID);
        }
        else{
            allSubjectsBackground.setVisible(false);
            subjectsControlGridPane.setVisible(false);
            allSubjectsGridPane.setVisible(true);
            editWindowGridPane.setVisible(false);
        }
    }

    public void updateGrades(List<Grade> gradesList, double average, double allAverage){
        gradesVBox.getChildren().clear();
        for(Grade grade : gradesList) {
            GridPane gridPane = new GridPane();
            gridPane.setMinHeight(30);
            gridPane.setMaxWidth(Double.MAX_VALUE);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPrefWidth(100.0);
            col1.setMinWidth(10.0);
            col1.setHgrow(Priority.SOMETIMES);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPrefWidth(40.0);
            col2.setMinWidth(10.0);
            col2.setMaxWidth(40.0);
            col2.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().addAll(col1, col2);

            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(30.0);
            row1.setMinHeight(10.0);
            row1.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row1);

            Label label1 = new Label(grade.getName());
            label1.setAlignment(Pos.CENTER_LEFT);
            GridPane.setMargin(label1, new Insets(10.0));
            GridPane.setRowIndex(label1, 0);
            GridPane.setColumnIndex(label1, 0);

            Label label2 = new Label(String.valueOf(grade.getValue()));
            label2.setAlignment(Pos.CENTER_LEFT);
            GridPane.setRowIndex(label2, 0);
            GridPane.setColumnIndex(label2, 1);

            gridPane.getChildren().addAll(label1, label2);
            gridPane.setId("eventList");
            VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));
            gradesVBox.getChildren().add(gridPane);
        }
        System.out.println("Average: "+average);
        DecimalFormat df = new DecimalFormat("0.00");
        averageLabel.setText(df.format(average));
        allAverageLabel.setText(df.format(allAverage));

    }

    public void updateAllSubjects(){
        DatabaseManager.getAllSubjectsInfo(this, currentUserID);
    }

    public void showAllSubjectsInfo(List<SubjectEvent> allSubjects){
        allSubjectsVBox.getChildren().clear();
        for(SubjectEvent subject : allSubjects){
            GridPane gridPane = new GridPane();
            gridPane.setMinHeight(30);
            gridPane.setMaxWidth(Double.MAX_VALUE);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPrefWidth(100.0);
            col1.setMinWidth(10.0);
            col1.setHgrow(Priority.SOMETIMES);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPrefWidth(30.0);
            col2.setMinWidth(30.0);
            col2.setMaxWidth(120.0);
            col2.setHgrow(Priority.SOMETIMES);

            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPrefWidth(20.0);
            col3.setMinWidth(20.0);
            col3.setMaxWidth(120.0);
            col3.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().addAll(col1, col2, col3);

            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(30.0);
            row1.setMinHeight(10.0);
            row1.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row1);

//            Label label1 = new Label("Elementy sztucznej inteligencji - Wykład (Hryhorii Shevchenko)");
            Label label1 = new Label(subject.getName()+" - "+subject.getSubjectType()+
                    " ("+subject.getPlace()+")");
            label1.setAlignment(Pos.CENTER);
            GridPane.setMargin(label1, new Insets(10.0));
            GridPane.setRowIndex(label1, 0);
            GridPane.setColumnIndex(label1, 0);
            GridPane.setColumnSpan(label1, 3);

            Button editButton = new Button("Edytuj informacje");
            editButton.setId("mainButton");
            editButton.setMnemonicParsing(false);
            editButton.setPickOnBounds(false);
            GridPane.setRowIndex(editButton, 0);
            GridPane.setColumnIndex(editButton, 1);
//            GridPane.setHalignment(editButton, Pos.CENTER);
//            GridPane.setValignment(editButton, Pos.CENTER);
            editButton.setVisible(false);
            editButton.setOnAction(event -> showEditWindow(subject.getIdSubEve()));

            Button deleteButton = new Button("Usuń przedmiot");
            deleteButton.setId("mainButton");
            deleteButton.setMnemonicParsing(false);
            deleteButton.setPickOnBounds(false);
            GridPane.setRowIndex(deleteButton, 0);
            GridPane.setColumnIndex(deleteButton, 2);
//            GridPane.setHalignment(deleteButton, Pos.LEFT);
//            GridPane.setValignment(deleteButton, Pos.CENTER);
            deleteButton.setVisible(false);
            deleteButton.setOnAction(event -> deleteSubject(subject.getIdSubEve()));


            gridPane.getChildren().addAll(label1, editButton, deleteButton);
            gridPane.setId("eventList");
            VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));
            gridPane.setOnMouseEntered(mouseEvent -> {
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            });
            gridPane.setOnMouseExited(mouseEvent -> {
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            });
            allSubjectsVBox.getChildren().add(gridPane);
        }
        allSubjectsScrollPane.setVvalue(1.1);
    }

    public void editComboBoxes(){
        createSubjectComboBox.getItems().add("Wykład");
        createSubjectComboBox.getItems().add("Laboratorium");
        createSubjectComboBox.getItems().add("Ćwiczenia");
        createSubjectComboBox.getItems().add("Projekt");
        createSubjectComboBox.getItems().add("Inne");
        createSubjectComboBox.getSelectionModel().select(0);
        subjectTypeComboBox.getItems().add("Wykład");
        subjectTypeComboBox.getItems().add("Laboratorium");
        subjectTypeComboBox.getItems().add("Ćwiczenia");
        subjectTypeComboBox.getItems().add("Projekt");
        subjectTypeComboBox.getItems().add("Inne");
        subjectTypeComboBox.getSelectionModel().select(0);
    }

    public void showEditWindow(int idsubjects){
        editSubjectID = idsubjects;
        DatabaseManager.getEditSubjectName(this, idsubjects, currentUserID);
        DatabaseManager.getEditSubjectDates(this, idsubjects, currentUserID);
        DatabaseManager.getEditSubjectGrades(this, idsubjects, currentUserID);

        allSubjectsGridPane.setVisible(false);
        editWindowGridPane.setVisible(true);
        addDateLabel.setVisible(false);
        addGradeLabel.setVisible(false);
        dateTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
        placeTextField.clear();
        gradeNameTextField.clear();
        valueTextField.clear();
    }

    public void goBack(){
        if (editWindowGridPane.isVisible()){
            DatabaseManager.getAllSubjectsInfo(this, currentUserID);
            editWindowGridPane.setVisible(false);
            allSubjectsGridPane.setVisible(true);
            addSubjectLabel.setVisible(false);
        }
        else if (subjectsControlGridPane.isVisible()){
            subjectsControlGridPane.setVisible(false);
            allSubjectsBackground.setVisible(false);
        }
    }

    public void updateEditSubjectName(String subjectName, String instructorName, int type,
                                      String mail, String room){
        subjectNameTextField.setText(subjectName);
        instructorNameTextField.setText(instructorName);
        subjectTypeComboBox.getSelectionModel().select(type);
        mailTextField.setText(mail);
        roomTextField.setText(room);
    }

    public void updateEditSubjectDate(List<SubjectEvent> subjects){
        editDateVBox.getChildren().clear();
        for (SubjectEvent subject: subjects){
            GridPane gridPane = new GridPane();
            gridPane.setMinHeight(30);
            gridPane.setMaxWidth(Double.MAX_VALUE);

            // Column constraints
            ColumnConstraints col1 = new ColumnConstraints(100, 100, Double.MAX_VALUE);
            col1.setHgrow(Priority.SOMETIMES);
            ColumnConstraints col2 = new ColumnConstraints(20, 20, Double.MAX_VALUE);
            col2.setHgrow(Priority.SOMETIMES);
            ColumnConstraints col3 = new ColumnConstraints(20, 20, Double.MAX_VALUE);
            col3.setHgrow(Priority.SOMETIMES);
            ColumnConstraints col4 = new ColumnConstraints(50, 50, Double.MAX_VALUE);
            col4.setHgrow(Priority.SOMETIMES);

            gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

            // Row constraints
            RowConstraints row1 = new RowConstraints(30, 30, 30);
            row1.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row1);

            // Labels and button
            Label dateLabel = new Label(subject.getDayName());
            GridPane.setHalignment(dateLabel, javafx.geometry.HPos.CENTER);

            Label startTimeLabel = new Label(subject.getStartTime());
            GridPane.setColumnIndex(startTimeLabel, 1);
            GridPane.setHalignment(startTimeLabel, javafx.geometry.HPos.CENTER);

            Label endTimeLabel = new Label(subject.getEndTime());
            GridPane.setColumnIndex(endTimeLabel, 2);
            GridPane.setHalignment(endTimeLabel, javafx.geometry.HPos.CENTER);

            Label locationLabel = new Label(subject.getPlace());
            GridPane.setColumnIndex(locationLabel, 3);
            GridPane.setHalignment(locationLabel, javafx.geometry.HPos.CENTER);

            Button removeButton = new Button("Usuń");
            removeButton.setMaxWidth(Double.MAX_VALUE);
            removeButton.setVisible(false);
            removeButton.setId("mainButton");
            GridPane.setColumnIndex(removeButton, 3);
            GridPane.setHalignment(removeButton, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(removeButton, javafx.geometry.VPos.CENTER);
            GridPane.setMargin(removeButton, new Insets(5, 5, 5, 5));

            gridPane.setOnMouseEntered(mouseEvent -> removeButton.setVisible(true));
            gridPane.setOnMouseExited(mouseEvent -> removeButton.setVisible(false));
            removeButton.setOnAction(event -> {
                if (DatabaseManager.deleteDate(subject.getDayName(), subject.getIdSubEve())) {
                    DatabaseManager.getEditSubjectDates(this, editSubjectID, currentUserID);
                    if (editSubjectID == currentSubjectID) updateSubjects(currentDate);
                    else DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
                }
            });

            // Add children to GridPane
            gridPane.getChildren().addAll(dateLabel, startTimeLabel, endTimeLabel, locationLabel, removeButton);
            gridPane.setId("eventList");
            VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));
            editDateVBox.getChildren().add(gridPane);
        }
    }

    public void updateEditSubjectGrades(List<Grade> grades){
        editGradeVBox.getChildren().clear();
        for (Grade grade : grades){
            GridPane gridPane = new GridPane();
            gridPane.setMinHeight(30);
            gridPane.setMaxWidth(Double.MAX_VALUE);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            col1.setMinWidth(10);
            col1.setPrefWidth(50);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            col2.setMinWidth(10);
            col2.setPrefWidth(50);

            gridPane.getColumnConstraints().addAll(col1, col2);

            // Row constraints
            RowConstraints row = new RowConstraints();
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            row.setMinHeight(10);
            row.setPrefHeight(30);
            row.setMaxHeight(30);

            gridPane.getRowConstraints().add(row);

            // Children
            Label label1 = new Label(grade.getName());
            GridPane.setHalignment(label1, javafx.geometry.HPos.CENTER);

            Label label2 = new Label(String.valueOf(grade.getValue()));
            GridPane.setColumnIndex(label2, 1);
            GridPane.setHalignment(label2, javafx.geometry.HPos.CENTER);

            Button button = new Button("Usuń");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMnemonicParsing(false);
            button.setVisible(false);
            button.setId("mainButton");
            GridPane.setColumnIndex(button, 1);
            GridPane.setHalignment(button, javafx.geometry.HPos.RIGHT);
            GridPane.setValignment(button, javafx.geometry.VPos.CENTER);
            GridPane.setMargin(button, new Insets(5, 5, 5, 90));

            gridPane.setOnMouseEntered(mouseEvent -> button.setVisible(true));
            gridPane.setOnMouseExited(mouseEvent -> button.setVisible(false));
            button.setOnAction(event -> {
                if (DatabaseManager.deleteGrade(grade.getDbID())) {
                    DatabaseManager.getEditSubjectGrades(this, editSubjectID, currentUserID);
                    if (editSubjectID == currentSubjectID)
                        DatabaseManager.getGradesInfo(this, "mainPage", currentUserID, currentSubjectID);
                }
            });

            gridPane.getChildren().addAll(label1, label2, button);
            gridPane.setId("eventList");
            VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));
            editGradeVBox.getChildren().add(gridPane);
        }
    }

    public void saveNotes(){
        boolean success = DatabaseManager.updateNotes(currentSubjectID,
                currentUserID, notesTextArea.getText());
        if(success){
            saveNotesLabel.setText("Pomyślnie zapisano");
            saveNotesLabel.setVisible(true);
        }
        else{
            saveNotesLabel.setText("Zapisanie nie powiodło się.");
            saveNotesLabel.setVisible(true);
        }
    }

    public void saveSubjectInfo(ActionEvent e){
        if(e.getTarget() == saveNameButton) DatabaseManager.updateSubjectInfo(editSubjectID,
                currentUserID, 'n', subjectNameTextField.getText());
        else if (e.getTarget() == saveInstructorButton) DatabaseManager.updateSubjectInfo(editSubjectID,
                currentUserID, 'i', instructorNameTextField.getText());
        else if (e.getTarget() == saveMailButton) DatabaseManager.updateSubjectInfo(editSubjectID,
                currentUserID, 'm', mailTextField.getText());
        else if (e.getTarget() == saveRoomButton) DatabaseManager.updateSubjectInfo(editSubjectID,
                currentUserID, 'r', roomTextField.getText());
        else if (e.getTarget() == subjectTypeComboBox) DatabaseManager.updateSubjectInfo(editSubjectID,
                currentUserID, 't', subjectTypeComboBox.getSelectionModel().getSelectedItem());
        if ((currentSubjectID == editSubjectID) && (currentSubjectID!=0)){
            DatabaseManager.getInstructorInfo(this, currentUserID, currentSubjectID);
        }
        DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
    }

    public void addSubjectDate(){
        String[] polishDaysOfWeek = {
                "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
        };
        String[] englishDaysOfWeek = {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };

        boolean dayEntered = false;
        LocalDate date = null;
        LocalTime startTime;
        LocalTime endTime;
        String dayName = dateTextField.getText();
        String startTimeString = startTimeTextField.getText();
        String endTimeString = endTimeTextField.getText();
        String place = placeTextField.getText();

        if (dayName.isEmpty() || startTimeString.isEmpty() ||
        endTimeString.isEmpty() || place.isEmpty() ){
            addDateLabel.setText("Nie podano wszystkich wartości.");
            addDateLabel.setVisible(true);
            return;
        }
        for (int i = 0; i<7; i++){
            {
                if (dayName.equalsIgnoreCase(polishDaysOfWeek[i])){
                    dayEntered = true;
                    dayName = englishDaysOfWeek[i];
                    break;
                }
            }
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            if (!dayEntered) {
                date = LocalDate.parse(dayName, dateFormatter);
            }
            startTime = LocalTime.parse(startTimeString, timeFormatter);
            endTime = LocalTime.parse(endTimeString, timeFormatter);

        } catch (DateTimeParseException e) {
            addDateLabel.setText("Niepoprawny format daty lub czasu.");
            addDateLabel.setVisible(true);
            return;
        }

        if (startTime.isAfter(endTime)){
            addDateLabel.setText("Przedział czasowy jest ujemny.");
            addDateLabel.setVisible(true);
            return;
        }

        if (DatabaseManager.addSubjectDate(currentUserID, editSubjectID,
                dayName, date, startTime, endTime, place)) {
            DatabaseManager.getEditSubjectDates(this, editSubjectID, currentUserID);
            addDateLabel.setText("Pomyślnie dodano.");
            addDateLabel.setVisible(true);
            dateTextField.clear();
            startTimeTextField.clear();
            endTimeTextField.clear();
            placeTextField.clear();
            DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
        }
    }

    public void addGrade(){
        double value;
        String text = gradeNameTextField.getText();
        String valueString = valueTextField.getText();
        if (text.isEmpty() || valueString.isEmpty()) {
            addGradeLabel.setText("Nie podano wszystkich wartości.");
            addGradeLabel.setVisible(true);
            return;
        }
        try{
            value = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            addGradeLabel.setText("Niepoprawny format liczby.");
            addGradeLabel.setVisible(true);
            return;
        }
        if (DatabaseManager.addGrade(currentUserID, editSubjectID,
                text, value)){
            DatabaseManager.getEditSubjectGrades(this, editSubjectID, currentUserID);
            addGradeLabel.setText("Pomyślnie dodano");
            addGradeLabel.setVisible(true);
            gradeNameTextField.clear();
            valueTextField.clear();
            if (editSubjectID == currentSubjectID)
                DatabaseManager.getGradesInfo(this, "mainPage", currentUserID, currentSubjectID);
        }

    }

    public void addSubject(){
        String name = addSubjectTextField.getText();
        String type = createSubjectComboBox.getSelectionModel().getSelectedItem();
        if (name.isEmpty() || type.isEmpty()) {
            addSubjectLabel.setText("Nie podano wszystkich wartości.");
            addSubjectLabel.setVisible(true);
            return;
        }
        if (DatabaseManager.addSubject(currentUserID, name, type)){
            DatabaseManager.getAllSubjectsInfo(this, currentUserID);
            addSubjectLabel.setText("Pomyślnie dodano.");
            addSubjectLabel.setVisible(true);
            addSubjectTextField.clear();
            createSubjectComboBox.getSelectionModel().select(0);
            allSubjectsScrollPane.setVvalue(1.2);
        }
        else {
            addSubjectLabel.setText("Dodanie nie powiodło się.");
        }
    }

    public void deleteSubject(int subjectID){
        if (DatabaseManager.deleteSubject(subjectID)){
            DatabaseManager.getAllSubjectsInfo(this, currentUserID);
            if (subjectID == currentSubjectID) updateSubjects(currentDate);
            else DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
        }
    }

    public void showEventsWindow(){
        if (eventsBackground.isVisible()){
            eventsBackground.setVisible(false);
            eventsWindow.setVisible(false);
        }
        else{
            eventsBackground.setVisible(true);
            eventsWindow.setVisible(true);
            DatabaseManager.getEvents(this, currentUserID);
            eventLabel.setVisible(false);
            eventDateTextField.clear();
            eventTimeTextField.clear();
            eventInfoTextField.clear();
        }
    }

    public void updateEvents(List<SubjectEvent> events){
        eventsVBox.getChildren().clear();
        for (SubjectEvent event : events){
            GridPane gridPane = new GridPane();
            gridPane.setMinHeight(30);
            gridPane.setMaxWidth(Double.MAX_VALUE);
            gridPane.setId("eventList");

            // Column constraints
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            col1.setMinWidth(10);
            col1.setPrefWidth(100);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            col2.setMaxWidth(120);
            col2.setMinWidth(10);
            col2.setPrefWidth(100);

            gridPane.getColumnConstraints().addAll(col1, col2);

            // Row constraints
            RowConstraints row1 = new RowConstraints();
            row1.setMinHeight(30);
            row1.setPrefHeight(30);
            row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

            gridPane.getRowConstraints().add(row1);

            // Label
            Label label = new Label(event.getDayName()+ " o "+event.getStartTime()+
                    "  |  "+event.getPlace());
            GridPane.setColumnSpan(label, 2);
            GridPane.setMargin(label, new Insets(0, 10, 0, 10));

            // Button
            Button button = new Button("Usuń wydarzenie");
            button.setId("mainButton");
            button.setMnemonicParsing(false);
            button.setVisible(false);
            GridPane.setColumnIndex(button, 1);
            GridPane.setHalignment(button, javafx.geometry.HPos.RIGHT);
            GridPane.setMargin(button, new Insets(0, 10, 0, 0));
            button.setOnAction(event1 -> {

                deleteEvent(event.getIdSubEve(), event.getType());
            });

            // Adding children to GridPane
            gridPane.getChildren().addAll(label, button);
            VBox.setMargin(gridPane, new Insets(0, 10, 5, 10));
            gridPane.setOnMouseEntered(e -> button.setVisible(true));
            gridPane.setOnMouseExited(e -> button.setVisible(false));

            // Adding GridPane to VBox
            eventsVBox.getChildren().add(gridPane);
        }

    }

    public void deleteEvent(int eventID, char type){
        if (DatabaseManager.deleteEvent(eventID, type)){
            DatabaseManager.getEvents(this, currentUserID);
            DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
        }
    }

    public void addEvent(){
        String[] polishDaysOfWeek = {
                "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
        };
        String[] englishDaysOfWeek = {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };

        boolean dayEntered = false;
        LocalDate date = null;
        LocalTime time;
        String dayName = eventDateTextField.getText();
        String timeString = eventTimeTextField.getText();
        String info = eventInfoTextField.getText();

        if (dayName.isEmpty() || timeString.isEmpty() || info.isEmpty() ){
            eventLabel.setText("Nie podano wszystkich wartości.");
            eventLabel.setVisible(true);
            return;
        }
        for (int i = 0; i<7; i++){
            {
                if (dayName.equalsIgnoreCase(polishDaysOfWeek[i])){
                    dayEntered = true;
                    dayName = englishDaysOfWeek[i];
                    break;
                }
            }
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            if (!dayEntered) {
                date = LocalDate.parse(dayName, dateFormatter);
            }
            time = LocalTime.parse(timeString, timeFormatter);

        } catch (DateTimeParseException e) {
            eventLabel.setText("Niepoprawny format daty lub czasu.");
            eventLabel.setVisible(true);
            return;
        }

        if (DatabaseManager.addEvent(currentUserID, dayName, date, time, info)) {
            eventLabel.setText("Pomyślnie dodano.");
            eventLabel.setVisible(true);
            eventDateTextField.clear();
            eventTimeTextField.clear();
            eventInfoTextField.clear();
            DatabaseManager.getEvents(this, currentUserID);
            DatabaseManager.getSubjectsInfo(this, currentUserID, currentDate);
        }
    }

    public void addHandler(Parent root){
        root.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getTarget()!=logoutGridPane){
                logoutGridPane.setVisible(false);
            }
        });
    }

    public void showLogoutGridPane(){
        logoutGridPane.setVisible(true);
    }

}
