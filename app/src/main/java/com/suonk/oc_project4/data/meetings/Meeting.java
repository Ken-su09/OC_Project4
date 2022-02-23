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
    private List<String> listOfMails;

    /**
     * Constructor
     *
     * @param id
     * @param subject
     * @param time
     */
    public Meeting(long id,
                   @NonNull String subject,
                   @NonNull String time,
                   @NonNull String place,
                   @NonNull String duration,
                   @NonNull List<String> listOfMails) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.duration = duration;
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
    public List<String> getListOfMails() {
        return listOfMails;
    }

    public void setListOfMails(@NonNull List<String> listOfMails) {
        this.listOfMails = listOfMails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", duration='" + duration + '\'' +
                ", listOfMails=" + listOfMails +
                '}';
    }
}
