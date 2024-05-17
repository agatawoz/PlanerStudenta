package com.example.login_page;

import java.time.LocalTime;

public class SubjectEvent {
    private char type;              // 's' for subject or 'e' for event
    private LocalTime startTime;
    private String name;
    private LocalTime endTime;      // only for subjects
    private String subjectType;     // only for subjects; "Wykład" / "Laboratorium" / etc
    private String place;

    public SubjectEvent(char type, LocalTime startTime, String name, LocalTime endTime,
                        String subjectType, String place) {
        this.type = type;
        this.startTime = startTime;
        this.name = name;
        this.endTime = endTime;
        this.subjectType = subjectType;
        this.place = place;
    }

    public char getType() {
        return type;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public String getPlace() {
        return place;
    }
}
