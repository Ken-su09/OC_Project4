package com.suonk.oc_project4.data.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.data.meetings.Meeting;

import java.util.List;

public interface DefaultMeetingRepository {

    /**
     * Get all my Meetings
     *
     * @return {@link List}
     */
    LiveData<List<Meeting>> getAllMeetings();

    /**
     * Add a new meeting
     */
    boolean addNewMeeting(@NonNull String subject,
                       int position,
                       @NonNull String time,
                       @NonNull String listOfMails);

    /**
     * Delete a meeting
     */
    void deleteMeeting(long id);
}
