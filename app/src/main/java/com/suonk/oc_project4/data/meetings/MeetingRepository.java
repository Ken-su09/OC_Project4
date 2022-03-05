package com.suonk.oc_project4.data.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository implements DefaultMeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Integer> id = new MutableLiveData<>();

    public void addNewMeeting(@NonNull String subject,
                              @NonNull String place,
                              @NonNull String time,
                              @NonNull String listOfMails) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        setId();
        assert meetings != null;
        meetings.add(new Meeting(id.getValue(), subject, place, time, listOfMails));

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

    @NonNull
    public MutableLiveData<List<Meeting>> getAllMeetings() {
        return meetingsLiveData;
    }

    public void setId() {
        if (id.getValue() == null) {
            id.setValue(1);
        } else {
            id.setValue(id.getValue() + 1);
        }
    }
}
