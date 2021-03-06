package com.suonk.oc_project4.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.MeetingRepository;

public class MeetingDetailsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public MeetingDetailsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    public LiveData<MeetingDetailsViewState> getMeetingDetailsLiveData(long id) {
        return Transformations.map(repository.getMeetingById(id), meeting ->
                new MeetingDetailsViewState(
                        meeting.getSubject(),
                        convertTimeToStartTime(meeting.getTime()),
                        convertTimeToEndTime(meeting.getTime()),
                        repository.getPosition(meeting.getPlace()),
                        meeting.getListOfMails()));
    }

    public String convertTimeToStartTime(@NonNull String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    public String convertTimeToEndTime(@NonNull String time) {
        String[] parts = time.split("to ");

        return parts[1];
    }
}
