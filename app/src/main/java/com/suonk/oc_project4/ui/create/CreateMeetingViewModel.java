package com.suonk.oc_project4.ui.create;

import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.di.Di;
import com.suonk.oc_project4.data.meetings.Meeting;

public class CreateMeetingViewModel extends ViewModel {

    public CreateMeetingViewModel() {
    }

    public void addNewMeeting(Meeting meeting) {
        Di.getDefaultMeetingRepository().addNewMeeting(meeting);
    }
}
