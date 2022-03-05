package com.suonk.oc_project4.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;
    private final LiveData<List<MeetingsViewState>> viewStateLiveData;

    public MeetingsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
        viewStateLiveData = Transformations.map(repository.getAllMeetings(), meetings -> {
            List<MeetingsViewState> meetingsViewState = new ArrayList<>();

            for (Meeting meeting : meetings) {
                meetingsViewState.add(
                        new MeetingsViewState(
                                meeting.getId(),
                                meeting.getSubject(),
                                meeting.getTime(),
                                meeting.getPlace(),
                                meeting.getListOfMails()
                        )
                );
            }

            return meetingsViewState;
        });
    }

    public LiveData<List<MeetingsViewState>> getAllMeetings() {
        return viewStateLiveData;
    }

//    public String listOfMailsToString(List<String> strings) {
//        return strings.stream().collect(Collectors.joining(", ", "", ""));
//    }

    public void onMeetingDelete(long id) {
        repository.deleteMeeting(id);
    }
}