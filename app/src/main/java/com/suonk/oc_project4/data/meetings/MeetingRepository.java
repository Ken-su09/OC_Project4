package com.suonk.oc_project4.data.meetings;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository implements DefaultMeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<Meeting>> getAllMeetings() {
        return meetingsLiveData;
    }

    public void addNewMeeting(Meeting meeting) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings != null) {
            meetings.add(meeting);
        }

        meetingsLiveData.setValue(meetings);
    }

    public void deleteMeeting(long id) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings != null) {
            for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
                Meeting meeting = iterator.next();
                if (meeting.getId() == id) {
                    iterator.remove();
                    break;
                }
            }
        }

        meetingsLiveData.setValue(meetings);
    }
}
