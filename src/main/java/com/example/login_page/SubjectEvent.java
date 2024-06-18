package com.example.login_page;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SubjectEvent {
    private char type;              // 's' for subject or 'e' for event
    private int idSubEve;
    private LocalTime startTime;
    private String name;
    private LocalTime endTime;      // only for subjects
    private String subjectType;     // only for subjects; "Wykład" / "Laboratorium" / etc
    private String place;

    public SubjectEvent(char type, int idSubEve, LocalTime startTime, String name, LocalTime endTime,
                        String subjectType, String place) {
        this.type = type;
        this.idSubEve = idSubEve;
        this.startTime = startTime;
        this.name = name;
        this.endTime = endTime;
        this.subjectType = subjectType;
        this.place = place;
    }

    public char getType() {
        return type;
    }

    public String getStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getName() {
        return name;
    }

    public String getEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getSubjectType() {
        return switch (subjectType) {
            case "w" -> "Wykład";
            case "l" -> "Laboratorium";
            case "ć" -> "Ćwiczenia";
            case "p" -> "Projekt";
            case "i" -> "Inne";
            default -> "Inne";
        };
    }

    public String getPlace() {
        return place;
    }

    public int getIdSubEve() {
        return idSubEve;
    }

    public void setIdSubEve(int idSubEve) {
        this.idSubEve = idSubEve;
    }

    public String getDayName(){
        return switch (this.name){
            case "Monday" -> "Poniedziałek";
            case "Tuesday" -> "Wtorek";
            case "Wednesday" -> "Środa";
            case "Thursday" -> "Czwartek";
            case "Friday" -> "Piątek";
            case "Saturday" -> "Sobota";
            case "Sunday" -> "Niedziela";
            default -> this.name;
        };
    }
}
