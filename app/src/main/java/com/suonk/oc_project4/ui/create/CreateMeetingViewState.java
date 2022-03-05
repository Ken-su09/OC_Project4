package com.suonk.oc_project4.ui.create;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CreateMeetingViewState {

    /**
     * Identifier
     */
    private final long id;

    /**
     * Subject
     */
    @NonNull
    private final String subject;

    /**
     * StartTime
     */
    @NonNull
    private final String startTime;

    /**
     * EndTime
     */
    @NonNull
    private final String endTime;

    /**
     * Place
     */
    @NonNull
    private final String place;

    /**
     * List of attendees
     */
    @NonNull
    private final String listOfMails;

    /**
     * Constructor
     */
    public CreateMeetingViewState(
            long id,
            @NonNull String subject,
            @NonNull String startTime,
            @NonNull String endTime,
            @NonNull String place,
            @NonNull String listOfMails) {
        this.id = id;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.listOfMails = listOfMails;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    @NonNull
    public String getEndTime() {
        return endTime;
    }

    @NonNull
    public String getPlace() {
        return place;
    }

    @NonNull
    public String getListOfMails() {
        return listOfMails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateMeetingViewState createMeetingViewState = (CreateMeetingViewState) o;
        return Objects.equals(id, createMeetingViewState.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateMeetingViewState{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", place='" + place + '\'' +
                ", listOfMails='" + listOfMails + '\'' +
                '}';
    }
}
