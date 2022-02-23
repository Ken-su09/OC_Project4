package com.suonk.oc_project4.ui.list;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;


public class MeetingsViewState {

    /**
     * Identifier
     */
    private final long id;

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
     * List of attendees
     */
    @NonNull
    private String listOfMails;

    /**
     * Constructor
     */
    public MeetingsViewState(
            long id,
            @NonNull String subject,
            @NonNull String time,
            @NonNull String place,
            @NonNull String listOfMails) {
        this.id = id;
        this.subject = subject;
        this.time = time;
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
    public String getTime() {
        return time;
    }

    @NonNull
    public String getPlace() {
        return place;
    }

    @NonNull
    public String getListOfMails() {
        return listOfMails.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewState meetingsViewState = (MeetingsViewState) o;
        return Objects.equals(id, meetingsViewState.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MeetingsViewState{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", listOfMails='" + listOfMails + '\'' +
                '}';
    }
}
