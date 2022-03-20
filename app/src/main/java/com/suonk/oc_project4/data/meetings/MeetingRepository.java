package com.suonk.oc_project4.data.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.ui.details.MeetingDetailsViewState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository implements DefaultMeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> id = new MutableLiveData<>();

    public void addNewMeeting(@NonNull String subject,
                              @NonNull String place,
                              @NonNull String time,
                              @NonNull String listOfMails) {
        List<Meeting> meetings = meetingsLiveData.getValue();
        setId();
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
    public LiveData<List<Meeting>> getAllMeetings() {
        return meetingsLiveData;
    }

    @NonNull
    public LiveData<Meeting> getMeetingById(long id) {
        MutableLiveData<Meeting> result = new MutableLiveData<>();
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings != null) {
            for (Meeting meeting : meetings) {
                if (meeting.getId() == id) {
                    result.setValue(meeting);
                }
            }
        }

        return result;
    }

    public void setId() {
        if (id.getValue() == null) {
            id.setValue(1);
        } else {
            id.setValue(id.getValue() + 1);
        }
    }
}
