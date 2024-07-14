package com.example.login_page;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public  class DatabaseManager {
    static String url = "jdbc:mysql://localhost:3306/planner";
    static String user = "hsh";
    static String password = "password";

    static Connection connection;

    static void createDBConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
        }
    }

    static int checkLoginData(String login, String password) {
        try {
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("idusers");
        } catch (SQLException e) {
            return 0;
        }
    }

    static void getSubjectsInfo(MainPage mainPage, int idusers, LocalDate date) {
        List<SubjectEvent> subjectsEvents = new ArrayList<>();
        try {
            String dayName = date.getDayOfWeek().name();

            String query = "SELECT 's', s.idsubjects ,cd.subject_starttime, cd.subject_endtime, " +
                    "s.name, s.type, cd.subject_place " +
                    "FROM subjects s " +
                    "JOIN subjects_calendar_daily cd ON s.idsubjects = cd.subjects_idsubjects " +
                    "WHERE s.users_idusers = ? AND cd.subject_date = ? " +
                    "UNION " +
                    "SELECT 's', s.idsubjects ,cw.subject_starttime, cw.subject_endtime, " +
                    "s.name, s.type, cw.subject_place " +
                    "FROM subjects s " +
                    "JOIN subjects_calendar_weekly cw ON s.idsubjects = cw.subjects_idsubjects " +
                    "WHERE s.users_idusers = ? AND cw.subject_day = ? " +
                    "UNION " +
                    "SELECT 'e', e.idevents ,ecd.event_time, NULL, " +
                    "e.name, NULL, e.info " +
                    "FROM events e " +
                    "JOIN events_calendar_daily ecd ON e.idevents = ecd.events_idevents " +
                    "WHERE e.users_idusers = ? AND ecd.event_date = ? " +
                    "UNION " +
                    "SELECT 'e', e.idevents ,ecw.event_time, NULL, " +
                    "e.name, NULL, e.info " +
                    "FROM events e " +
                    "JOIN events_calendar_weekly ecw ON e.idevents = ecw.events_idevents " +
                    "WHERE e.users_idusers = ? AND ecw.event_day = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idusers);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setInt(3, idusers);
            preparedStatement.setString(4, dayName);
            preparedStatement.setInt(5, idusers);
            preparedStatement.setString(6, date.toString());
            preparedStatement.setInt(7, idusers);
            preparedStatement.setString(8, dayName);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                char type = resultSet.getString(1).charAt(0);
                int idSubEve = resultSet.getInt(2);
                LocalTime startTime = resultSet.getTime(3).toLocalTime();
                String name = resultSet.getString(5);
                LocalTime endTime;
                String subjectType;
                String place;
                if (type == 's') {
                    endTime = resultSet.getTime(4).toLocalTime();
                } else {
                    endTime = null;
                }
                subjectType = resultSet.getString(6);
                place = resultSet.getString(7);

                SubjectEvent subjectEvent = new SubjectEvent(type, idSubEve, startTime, name, endTime,
                        subjectType, place);
                subjectsEvents.add(subjectEvent);
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
        subjectsEvents.sort(Comparator.comparing(SubjectEvent::getStartTime));
        mainPage.showSubjects(subjectsEvents);
    }

    static void getInstructorInfo(MainPage mainPage, int currentUserID, int idsubjects) {
        try {
            String query = "SELECT s.name, i.name, i.mail, i.room, s.notes FROM instructors i " +
                    "JOIN subjects s ON s.idsubjects = i.subjects_idsubjects " +
                    "WHERE i.subjects_users_idusers = ? AND i.subjects_idsubjects = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            preparedStatement.setInt(2, idsubjects);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String subjectName = resultSet.getString(1);
            String name = resultSet.getString(2);
            String mail = resultSet.getString(3);
            String room = resultSet.getString(4);
            String notes = resultSet.getString(5);
            mainPage.updateInstructorInfo(subjectName, name, mail, room);
            mainPage.updateNotes(notes);
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
    }

    static void getGradesInfo(MainPage mainPage, String source, int currentUserID, int idsubjects) {
        List<Grade> gradesList = new ArrayList<>();
        double average = 0;
        double allAverage = 0;
        try {
            String query = "SELECT g.idgrades, g.name, g.grade " +
                    "FROM grades g " +
                    "WHERE g.subjects_users_idusers = ? AND g.subjects_idsubjects = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            preparedStatement.setInt(2, idsubjects);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int dbID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double value = resultSet.getDouble(3);
                Grade grade = new Grade(dbID, name, value);
                gradesList.add(grade);
            }

            query = "SELECT AVG(g.grade) " +
                    "FROM grades g " +
                    "WHERE g.subjects_users_idusers = ? AND g.subjects_idsubjects = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            preparedStatement.setInt(2, idsubjects);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            average = resultSet.getDouble(1);

            query = "SELECT AVG(g.grade) " +
                    "FROM grades g " +
                    "WHERE g.subjects_users_idusers = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            allAverage = resultSet.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
        mainPage.updateGrades(gradesList, average, allAverage);
    }

    static void getAllSubjectsInfo(MainPage mainPage, int currentUserID) {
        List<SubjectEvent> allSubjects = new ArrayList<>();
        try {
            String query = "SELECT s.idsubjects, s.name, s.type, i.name " +
                    "FROM subjects s " +
                    "JOIN instructors i ON i.subjects_idsubjects = s.idsubjects " +
                    "WHERE s.users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int subjectID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String subjectType = resultSet.getString(3);
                String place = resultSet.getString(4);
                SubjectEvent subject = new SubjectEvent('s', subjectID, null,
                        name, null, subjectType, place);
                allSubjects.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
        mainPage.showAllSubjectsInfo(allSubjects);
    }

    static void getEditSubjectName(MainPage mainPage, int idsubjects, int currentUserID) {
        try {
            String query = "SELECT s.name, i.name, s.type, i.mail, i.room " +
                    "FROM subjects s " +
                    "JOIN instructors i ON s.idsubjects = i.subjects_idsubjects " +
                    "WHERE s.idsubjects = ? AND s.users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idsubjects);
            preparedStatement.setInt(2, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String subjectName = resultSet.getString(1);
            String instructorName = resultSet.getString(2);
            int type = switch (resultSet.getString(3).charAt(0)) {
                case 'w' -> 0;
                case 'l' -> 1;
                case 'ć' -> 2;
                case 'p' -> 3;
                default -> 4;
            };
            String mail = resultSet.getString(4);
            String room = resultSet.getString(5);
            mainPage.updateEditSubjectName(subjectName, instructorName, type, mail, room);
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
    }

    static void getEditSubjectDates(MainPage mainPage, int idsubjects, int currentUserID) {
        List<SubjectEvent> subjects = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            String query = "SELECT cw.idsubjects_calendar_weekly, cw.subject_day, " +
                    "cw.subject_starttime, cw.subject_endtime, cw.subject_place " +
                    "FROM subjects_calendar_weekly cw " +
                    "WHERE cw.subjects_idsubjects = ? AND cw.subjects_users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idsubjects);
            preparedStatement.setInt(2, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int subjectID = resultSet.getInt(1);
                String day = resultSet.getString(2);
                LocalTime startTime = resultSet.getTime(3).toLocalTime();
                LocalTime endTime = resultSet.getTime(4).toLocalTime();
                String place = resultSet.getString(5);
                SubjectEvent subject = new SubjectEvent('w', subjectID, startTime,
                        day, endTime, "i", place);
                subjects.add(subject);
            }

            query = "SELECT cd.idsubjects_calendar_daily, cd.subject_date, " +
                    "cd.subject_starttime, cd.subject_endtime, cd.subject_place " +
                    "FROM subjects_calendar_daily cd " +
                    "WHERE cd.subjects_idsubjects = ? AND cd.subjects_users_idusers = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idsubjects);
            preparedStatement.setInt(2, currentUserID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int subjectID = resultSet.getInt(1);
                String date = dateFormat.format(resultSet.getDate(2));
                LocalTime startTime = resultSet.getTime(3).toLocalTime();
                LocalTime endTime = resultSet.getTime(4).toLocalTime();
                String place = resultSet.getString(5);
                SubjectEvent subject = new SubjectEvent('d', subjectID, startTime,
                        date, endTime, "i", place);
                subjects.add(subject);
            }
            mainPage.updateEditSubjectDate(subjects);

        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
    }

    static void getEditSubjectGrades(MainPage mainPage, int idsubjects, int currentUserID) {
        List<Grade> grades = new ArrayList<>();
        try {
            String query = "SELECT g.idgrades, g.name, g.grade " +
                    "FROM grades g " +
                    "WHERE g.subjects_idsubjects = ? AND g.subjects_users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idsubjects);
            preparedStatement.setInt(2, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int dbID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double value = resultSet.getDouble(3);
                Grade grade = new Grade(dbID, name, value);
                grades.add(grade);
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
        mainPage.updateEditSubjectGrades(grades);
    }

    static boolean updateNotes(int idsubjects, int currentUserID, String text) {
        try {
            String query = "UPDATE subjects " +
                    "SET notes = ? " +
                    "WHERE idsubjects = ? AND users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, idsubjects);
            preparedStatement.setInt(3, currentUserID);
            int rows = preparedStatement.executeUpdate();
            return rows != 0;
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
            return false;
        }
    }

    static void updateSubjectInfo(int idsubjects, int currentUserID, char type, String text) {
        if (type == 't') {
            text = switch (text) {
                case "Wykład" -> "w";
                case "Laboratorium" -> "l";
                case "Ćwiczenia" -> "ć";
                case "Projekt" -> "p";
                default -> "i";
            };
        }
        try {
            String query = switch (type) {
                case 'n' -> "UPDATE subjects " +
                        "SET name = ? " +
                        "WHERE idsubjects = ? AND users_idusers = ?";
                case 'i' -> "UPDATE instructors " +
                        "SET name = ? " +
                        "WHERE subjects_idsubjects = ? AND subjects_users_idusers = ?";
                case 'm' -> "UPDATE instructors " +
                        "SET mail = ? " +
                        "WHERE subjects_idsubjects = ? AND subjects_users_idusers = ?";
                case 'r' -> "UPDATE instructors " +
                        "SET room = ? " +
                        "WHERE subjects_idsubjects = ? AND subjects_users_idusers = ?";
                default -> "UPDATE subjects " +
                        "SET type = ? " +
                        "WHERE idsubjects = ? AND users_idusers = ?";
            };
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, idsubjects);
            preparedStatement.setInt(3, currentUserID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
        }
    }

    static boolean addSubjectDate(int currentUserID, int editSubjectID,
                                  String dayName, LocalDate date, LocalTime startTime,
                                  LocalTime endTime, String place) {
        try {
            String query;
            PreparedStatement preparedStatement = null;
            if (date == null) {
                query = "INSERT INTO subjects_calendar_weekly (subject_day, " +
                        "subject_starttime, subject_endtime, subject_place, " +
                        "subjects_idsubjects, subjects_users_idusers) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, dayName);
                preparedStatement.setTime(2, Time.valueOf(startTime));
                preparedStatement.setTime(3, Time.valueOf(endTime));
                preparedStatement.setString(4, place);
                preparedStatement.setInt(5, editSubjectID);
                preparedStatement.setInt(6, currentUserID);
            } else {
                query = "INSERT INTO subjects_calendar_daily (subject_date, " +
                        "subject_starttime, subject_endtime, subject_place, " +
                        "subjects_idsubjects, subjects_users_idusers) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setTime(2, Time.valueOf(startTime));
                preparedStatement.setTime(3, Time.valueOf(endTime));
                preparedStatement.setString(4, place);
                preparedStatement.setInt(5, editSubjectID);
                preparedStatement.setInt(6, currentUserID);
            }
            int rows = preparedStatement.executeUpdate();
            return rows != 0;
        } catch (SQLException | AssertionError e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
            return false;
        }
    }

    static boolean addGrade(int currendUserID, int editSubjectID, String gradeName, double value) {
        try {
            String query = "INSERT INTO grades (name, grade, " +
                    "subjects_idsubjects, subjects_users_idusers) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gradeName);
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(value));
            preparedStatement.setInt(3, editSubjectID);
            preparedStatement.setInt(4, currendUserID);
            int rows = preparedStatement.executeUpdate();
            return rows != 0;
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
            return false;
        }
    }

    static boolean addSubject(int currentUserID, String name, String type) {
        type = switch (type) {
            case "Wykład" -> "w";
            case "Laboratorium" -> "l";
            case "Ćwiczenia" -> "ć";
            case "Projekt" -> "p";
            default -> "i";
        };
        try {
            String query = "INSERT INTO subjects(name, type, notes, users_idusers) " +
                    "VALUES (?, ?, '', ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, currentUserID);
            int rows = preparedStatement.executeUpdate();
            if (rows != 0) {
                long id;
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                    query = "INSERT INTO instructors (name, mail, room, " +
                            "subjects_idsubjects, subjects_users_idusers) " +
                            "VALUES ('Nie podano danych', '?', '?', ?, ?)";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, (int) id);
                    preparedStatement.setInt(2, currentUserID);
                    rows = preparedStatement.executeUpdate();
                    return rows != 0;
                }

            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            e.printStackTrace();
            return false;
        }
    }

    static boolean deleteSubject(int subjectID) {
        try {
            String[] queries = new String[5];
            queries[0] = "DELETE FROM instructors WHERE subjects_idsubjects = ?";
            queries[1] = "DELETE FROM subjects_calendar_daily WHERE subjects_idsubjects = ?";
            queries[2] = "DELETE FROM subjects_calendar_weekly WHERE subjects_idsubjects = ?";
            queries[3] = "DELETE FROM grades WHERE subjects_idsubjects = ?";
            queries[4] = "DELETE FROM subjects WHERE idsubjects = ?";
            for (String query : queries) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, subjectID);
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean deleteDate(String text, int dateID) {
        int i;
        String tableToUse = null;
        String[] polishDaysOfWeek = {
                "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
        };
        for (i = 0; i < 7; i++) {
            if (Objects.equals(text, polishDaysOfWeek[i])) {
                tableToUse = "subjects_calendar_weekly";
                break;
            }
        }
        if (tableToUse == null) tableToUse = "subjects_calendar_daily";

        try {
            String query = "DELETE FROM " + tableToUse + " WHERE id" + tableToUse + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, dateID);
            int rows = preparedStatement.executeUpdate();
            return rows != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean deleteGrade(int gradeID) {
        try {
            String query = "DELETE FROM grades WHERE idgrades = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gradeID);
            int rows = preparedStatement.executeUpdate();
            return rows != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void getEvents(MainPage mainPage, int currentUserID) {
        List<SubjectEvent> events = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            String query = "SELECT ew.events_idevents, ew.event_day, ew.event_time, e.info " +
                    "FROM events_calendar_weekly ew " +
                    "JOIN events e ON e.idevents = ew.events_idevents " +
                    "WHERE e.users_idusers = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int eventID = resultSet.getInt(1);
                String day = resultSet.getString(2);
                LocalTime time = resultSet.getTime(3).toLocalTime();
                String info = resultSet.getString(4);
                SubjectEvent event = new SubjectEvent('w', eventID, time,
                        day, null, "i", info);
                events.add(event);
            }

            query = "SELECT ed.events_idevents, ed.event_date, ed.event_time, e.info " +
                    "FROM events_calendar_daily ed " +
                    "JOIN events e ON e.idevents = ed.events_idevents " +
                    "WHERE e.users_idusers = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int eventID = resultSet.getInt(1);
                String date = dateFormat.format(resultSet.getDate(2));
                LocalTime time = resultSet.getTime(3).toLocalTime();
                String info = resultSet.getString(4);
                SubjectEvent event = new SubjectEvent('d', eventID, time,
                        date, null, "i", info);
                events.add(event);
            }
            mainPage.updateEvents(events);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean deleteEvent(int eventID, char type) {
        String table;
        if (type == 'w') table = "events_calendar_weekly";
        else table = "events_calendar_daily";
        try {
            String query = "DELETE FROM " + table + " WHERE events_idevents = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, eventID);
            int rows = preparedStatement.executeUpdate();
            if (rows != 0) {
                query = "DELETE FROM events WHERE idevents = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, eventID);
                rows = preparedStatement.executeUpdate();
                if (rows != 0) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean addEvent(int currentUserID, String dayName, LocalDate date,
                            LocalTime time, String info) {
        try {
            String query = "INSERT INTO events(name, info, users_idusers) " +
                    "VALUES ('?', ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, info);
            preparedStatement.setInt(2, currentUserID);
            int rows = preparedStatement.executeUpdate();
            if (rows != 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    int eventID = resultSet.getInt(1);
                    if (date == null) {
                        query = "INSERT INTO events_calendar_weekly (event_day, " +
                                "event_time, events_idevents, events_users_idusers) " +
                                "VALUES (?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, dayName);
                        preparedStatement.setTime(2, Time.valueOf(time));
                        preparedStatement.setInt(3, eventID);
                        preparedStatement.setInt(4, currentUserID);
                        rows = preparedStatement.executeUpdate();
                        return rows != 0;
                    } else {
                        query = "INSERT INTO events_calendar_daily (event_date, " +
                                "event_time, events_idevents, events_users_idusers) " +
                                "VALUES (?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setDate(1, Date.valueOf(date));
                        preparedStatement.setTime(2, Time.valueOf(time));
                        preparedStatement.setInt(3, eventID);
                        preparedStatement.setInt(4, currentUserID);
                        rows = preparedStatement.executeUpdate();
                        return rows != 0;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static int checkNew(String login, String password) {
        try {
            String query = "SELECT * FROM users WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return -1;

            query = "INSERT INTO users (login, password) " +
                    "VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
