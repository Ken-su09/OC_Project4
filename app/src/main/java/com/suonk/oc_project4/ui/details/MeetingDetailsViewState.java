package com.suonk.oc_project4.ui.details;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MeetingDetailsViewState {

    /**
     * Subject
     */
    @NonNull
    private String subject;

    /**
     * Time
     */
    @NonNull
    private final String startTime;

    /**
     * Time
     */
    @NonNull
    private final String endTime;

    /**
     * Place
     */
    @NonNull
    private int place;

    /**
     * List of attendees
     */
    @NonNull
    private String listOfMails;

    /**
     * Constructor
     *
     * @param subject
     * @param startTime
     * @param endTime
     */
    public MeetingDetailsViewState(
            @NonNull String subject,
            @NonNull String startTime,
            @NonNull String endTime,
            @NonNull int place,
            @NonNull String listOfMails) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.listOfMails = listOfMails;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    @NonNull
    public String getEndTime() {
        return endTime;
    }

    public int getPlace() {
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
        MeetingDetailsViewState meeting = (MeetingDetailsViewState) o;
        return Objects.equals(subject, meeting.subject);
    }

    @Override
    public String toString() {
        return "MeetingDetailsViewState{" +
                "subject='" + subject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", place='" + place + '\'' +
                ", listOfMails='" + listOfMails + '\'' +
                '}';
    }
}
