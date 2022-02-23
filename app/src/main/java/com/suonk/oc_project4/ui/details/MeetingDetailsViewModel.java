package com.suonk.oc_project4.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.list.MeetingsViewState;

public class MeetingDetailsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public MeetingDetailsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

//    public LiveData<MeetingDetailsViewState> getMeetingSelected(long id) {
//        Transformations.map(repository.getAllMeetings(), meetings -> {
//            MeetingsViewState meetingViewState;
//
//            for (Meeting meeting : meetings) {
//                if (meeting.getId() == id) {
//                    meetingViewState = (MeetingsViewState) meeting;
//                    break;
//                }
//            }
//
//            return meetingViewState;
//        });
//    }
}
