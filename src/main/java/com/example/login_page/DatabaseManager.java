package com.example.login_page;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public  class DatabaseManager {
    static String url = "jdbc:mysql://localhost:3306/planner";
    static String user = "hsh";
    static String password = "password";

    static Connection connection;

    static void createDBConnection (){
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
        }
    }

    static boolean checkLoginData (String login, String password){
        try {
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            System.out.println("Error while connecting to database");
            return false;
        }
    }

    static void getSubjectsInfo(MainPage mainPage, String userLogin, LocalDate date){
        List<SubjectEvent> subjectsEvents = new ArrayList<>();
        try {
            String dayName = date.getDayOfWeek().name();

            String query = "SELECT 's' ,cd.subject_starttime, cd.subject_endtime, " +
                    "s.name, s.type, cd.subject_place " +
                    "FROM subjects s " +
                    "JOIN subjects_calendar_daily cd ON s.idsubjects = cd.idsubjects " +
                    "WHERE s.login = ? AND cd.subject_date = ? " +
                    "UNION " +
                    "SELECT 's', cw.subject_starttime, cw.subject_endtime, " +
                    "s.name, s.type, cw.subject_place " +
                    "FROM subjects s " +
                    "JOIN subjects_calendar_weekly cw ON s.idsubjects = cw.idsubjects " +
                    "WHERE s.login = ? AND cw.subject_day = ? " +
                    "ORDER BY subjects_calendar_daily.starttime, subjects_calendar_weekly.starttime " +
                    "UNION " +
                    "SELECT 'e', ecd.event_time, NULL, e.info, NULL, NULL " +
                    "FROM events e " +
                    "JOIN events_calendar_daily ecd ON e.idevents = ecd.idevents " +
                    "WHERE e.login = ? AND ecd.event_date = ? " +
                    "UNION " +
                    "SELECT 'e', ecw.event_time, NULL, e.info, NULL, NULL " +
                    "FROM events e " +
                    "JOIN events_calendar_weekly ecw ON e.idevents = ecw.idevents " +
                    "WHERE e.login = ? AND ecw.event_day = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setString(3, userLogin);
            preparedStatement.setString(4, dayName);
            preparedStatement.setString(5, userLogin);
            preparedStatement.setString(6, dayName);
            preparedStatement.setString(7, userLogin);
            preparedStatement.setString(8, dayName);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                char type = resultSet.getString(1).charAt(0);
                LocalTime startTime = resultSet.getTime(2).toLocalTime();
                String name = resultSet.getString(3);
                LocalTime endTime;
                String subjectType;
                String place;
                if (type == 's'){
                    endTime = resultSet.getTime(4).toLocalTime();
                }
                else {
                    endTime = null;
                }
                subjectType = resultSet.getString(5);
                place = resultSet.getString(6);

                SubjectEvent subjectEvent = new SubjectEvent(type, startTime, name, endTime,
                        subjectType, place);
                subjectsEvents.add(subjectEvent);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while connecting to database");
        }
        subjectsEvents.sort(Comparator.comparing(SubjectEvent::getStartTime));
        mainPage.showSubjects(subjectsEvents);
    }



}
