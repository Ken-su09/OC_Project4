package com.suonk.oc_project4.ui.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

public class CreateMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public CreateMeetingViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public void addNewMeeting(Meeting meeting) {
        repository.addNewMeeting(meeting);
    }

    public void addNewMeetingViewState(CreateMeetingViewState meeting) {
    }
}
