package com.suonk.oc_project4.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public MeetingsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<MeetingsViewState>> getAllMeetings() {
        return Transformations.map(repository.getAllMeetings(), meetings -> {
            List<MeetingsViewState> meetingsViewState = new ArrayList<>();

            for (Meeting meeting : meetings) {
                meetingsViewState.add(
                        new MeetingsViewState(
                                meeting.getId(),
                                meeting.getSubject(),
                                meeting.getTime(),
                                meeting.getPlace(),
                                meeting.getListOfMails().toString()
//                                listOfMailsToString(meeting.getListOfMails())
                        )
                );
            }

            return meetingsViewState;
        });
    }

//    public String listOfMailsToString(List<String> strings) {
//        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
//            String String = iterator.next();
//        }
//        meeting.getListOfMails().toString()
//        repository.deleteMeeting(id);
//    }

    public void onMeetingDelete(long id) {
        repository.deleteMeeting(id);
    }
}