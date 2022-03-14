package com.suonk.oc_project4.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingDetailsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    private LiveData<MeetingDetailsViewState> meetingsDetailsLiveData;

    public MeetingDetailsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public void getMeetingSelected(long id) {
        meetingsDetailsLiveData = Transformations.map(repository.getAllMeetings(), meetings -> {
            MeetingDetailsViewState meetingsViewState = null;

            for (Meeting meeting : meetings) {
                if (meeting.getId() == id) {
                    meetingsViewState = new MeetingDetailsViewState(meeting.getSubject(),
                            convertTimeToStartTime(meeting.getTime()),
                            convertTimeToEndTime(meeting.getTime()), meeting.getPlace(), meeting.getListOfMails());
                    break;
                }
            }
            return meetingsViewState;
        });
    }

    public LiveData<MeetingDetailsViewState> getMeetingDetailsLiveData() {
        return meetingsDetailsLiveData;
    }

    public String convertTimeToStartTime(String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    public String convertTimeToEndTime(String time) {
        String[] parts = time.split("to ");

        return parts[1];
    }

    public int placeToArrayAdapterPosition(String place) {
        String[] places = {"Peach", "Mario", "Luigi", "Bowser", "Toad", "Yoshi", "Daisy", "Donkey Kong"};
        for (int i = 0; i < places.length; i++) {
            if (place.equals(places[i])) {
                return i;
            }
        }
        return 0;
    }
}
