package com.suonk.oc_project4.data.meetings;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Meeting
 */
public class Meeting {

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
     * Place
     */
    @NonNull
    private String place;

    /**
     * Time
     */
    @NonNull
    private String time;

    /**
     * List of attendees
     */
    @NonNull
    private final String listOfMails;

    /**
     * Constructor
     *
     * @param id
     * @param subject
     * @param time
     */
    public Meeting(long id,
                   @NonNull String subject,
                   @NonNull String place,
                   @NonNull String time,
                   @NonNull String listOfMails) {
        this.id = id;
        this.subject = subject;
        this.place = place;
        this.time = time;
        this.listOfMails = listOfMails;
    }

    public long getId() {
        return id;
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

    @NonNull
    public String getListOfMails() {
        return listOfMails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && subject.equals(meeting.subject) && place.equals(meeting.place) && time.equals(meeting.time) && listOfMails.equals(meeting.listOfMails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, place, time, listOfMails);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", listOfMails=" + listOfMails +
                '}';
    }
}
