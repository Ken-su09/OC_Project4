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
    private String time;

    /**
     * Place
     */
    @NonNull
    private String place;

    /**
     * Duration
     */
    @NonNull
    private String duration;

    /**
     * List of attendees
     */
    @NonNull
    private String listOfMails;

    /**
     * Constructor
     *
     * @param subject
     * @param time
     */
    public MeetingDetailsViewState(
                   @NonNull String subject,
                   @NonNull String time,
                   @NonNull String place,
                   @NonNull String duration,
                   @NonNull String listOfMails) {
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.duration = duration;
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
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public String getPlace() {
        return place;
    }

    public void setPlace(@NonNull String place) {
        this.place = place;
    }

    @NonNull
    public String getDuration() {
        return duration;
    }

    public void setDuration(@NonNull String duration) {
        this.duration = duration;
    }

    @NonNull
    public String getListOfMails() {
        return listOfMails;
    }

    public void setListOfMails(@NonNull String listOfMails) {
        this.listOfMails = listOfMails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingDetailsViewState meeting = (MeetingDetailsViewState) o;
        return Objects.equals(subject, meeting.subject);
    }
}
