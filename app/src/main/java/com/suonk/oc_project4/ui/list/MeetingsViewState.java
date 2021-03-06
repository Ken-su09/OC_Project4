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
    private final String subject;

    /**
     * Time
     */
    @NonNull
    private final String time;

    /**
     * Place
     */
    @NonNull
    private final String place;

    /**
     * Color
     */
    private final int color;

    /**
     * List of attendees
     */
    @NonNull
    private final String listOfMails;

    /**
     * Constructor
     */
    public MeetingsViewState(
            long id,
            @NonNull String subject,
            @NonNull String time,
            @NonNull String place,
            @NonNull String listOfMails,
            int color) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.listOfMails = listOfMails;
        this.color = color;
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
        return listOfMails;
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewState that = (MeetingsViewState) o;
        return id == that.id && subject.equals(that.subject) && time.equals(that.time) && place.equals(that.place) && listOfMails.equals(that.listOfMails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, time, place, listOfMails);
    }

    @NonNull
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
