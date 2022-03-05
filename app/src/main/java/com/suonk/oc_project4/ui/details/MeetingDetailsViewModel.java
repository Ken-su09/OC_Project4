package com.suonk.oc_project4.ui.details;

import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.create.CreateMeetingFragment;
import com.suonk.oc_project4.ui.list.MeetingsViewState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MeetingDetailsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public MeetingDetailsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public LiveData<MeetingDetailsViewState> getMeetingSelected(long id, @NonNull MeetingDetailsFragment fragment) {
        return Transformations.map(repository.getAllMeetings(), meetings -> {
            MeetingDetailsViewState meetingsViewState = null;

            for (Meeting meeting : meetings) {
                if (meeting.getId() == id) {
                    meetingsViewState = new MeetingDetailsViewState(meeting.getSubject(),
                            convertTimeToStartTime(meeting.getTime()),
                            convertTimeToEndTime(meeting.getTime()), meeting.getPlace(), meeting.getListOfMails().toString());

                    String[] emailArray = meeting.getListOfMails().split(",");
                    for (String email : emailArray) {
                        fragment.initChip(email);
                    }
                    break;
                }
            }

            return meetingsViewState;
        });
    }

    private String convertTimeToStartTime(String time) {
        String[] parts = time.split("to");

        return parts[0];
    }

    private String convertTimeToEndTime(String time) {
        String[] parts = time.split("to");

        return parts[1];
    }

//    public void initChipGroup(@NonNull MeetingDetailsFragment fragment) {
//        for (int i = 0; i < listOfMails.size(); i++) {
//            fragment.initChip(listOfMails.get(i));
//        }
//    }
}
