package com.suonk.oc_project4.data.meetings;

import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.data.meetings.Meeting;

import java.util.List;

public interface DefaultMeetingRepository {

    /**
     * Get all my Meetings
     *
     * @return {@link List}
     */
    MutableLiveData<List<Meeting>> getAllMeetings();

    /**
     * Add a new meeting
     */
    void addNewMeeting(Meeting meeting);

    /**
     * Delete a meeting
     */
    void deleteMeeting(long id);
}
