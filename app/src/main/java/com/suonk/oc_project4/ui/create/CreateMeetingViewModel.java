package com.suonk.oc_project4.ui.create;

import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.espresso.ViewAction;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.events.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CreateMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    private final MutableLiveData<CreateMeetingViewState> createMeetingViewState = new MutableLiveData<>();

    public CreateMeetingViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
        createMeetingViewState.setValue(new CreateMeetingViewState("", "", "", "", ""));
    }

    public void createMeeting(@NonNull String subject, @NonNull String place, @NonNull String time,
                              @NonNull String listOfMails) {
        createMeetingViewState.setValue(new CreateMeetingViewState(subject, convertTimeToStartTime(time),
                convertTimeToEndTime(time), place, listOfMails));
        repository.addNewMeeting(subject, place, time, listOfMails);
    }

    @NonNull
    public LiveData<CreateMeetingViewState> getCreateMeetingViewState() {
        return createMeetingViewState;
    }


    public String convertStartAndEndTimeToOneString(@NonNull String startTime, @NonNull String endTime) {
        return startTime + " to " + endTime;
    }

    public Boolean checkIfEndTimeSuperiorThanStartTime(@NonNull String startTime, @NonNull String endTime) {
        int timeFromInt;
        int timeToInt;
        if (startTime.charAt(1) == 'h') {
            timeFromInt = Integer.parseInt(String.valueOf(startTime.charAt(0)));
        } else {
            timeFromInt = Integer.parseInt(String.valueOf(startTime.charAt(0)) + String.valueOf(startTime.charAt(1)));
        }
        if (endTime.charAt(1) == 'h') {
            timeToInt = Integer.parseInt(String.valueOf(endTime.charAt(0)));
        } else {
            timeToInt = Integer.parseInt(String.valueOf(endTime.charAt(0)) + String.valueOf(endTime.charAt(1)));
        }

        return timeFromInt < timeToInt;
    }

    public boolean checkIfFieldsNotEmpty(@NonNull String subject, @NonNull String startTime,
                                         @NonNull String endTime,
                                         @NonNull String place) {
        return (!subject.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty() && !place.isEmpty());
    }

    public boolean checkIfEmailValid(@NonNull String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        matcher = pattern.matcher(email);

        return matcher.matches();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String convertChipGroupToString(@NonNull ChipGroup chipGroup) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            String chip = ((Chip) chipGroup.getChildAt(i)).getText().toString();
            list.add(chip);
        }

        return list.stream().collect(Collectors.joining(", ", "", ""));
    }

    public List<String> convertChipGroupToList(@NonNull ChipGroup chipGroup) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            String chip = ((Chip) chipGroup.getChildAt(i)).getText().toString();
            list.add(chip);
        }

        return list;
    }

    public String convertTimeToStartTime(String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    public String convertTimeToEndTime(String time) {
        String[] parts = time.split("to ");

        return parts[1];
    }
}
