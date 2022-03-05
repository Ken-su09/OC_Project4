package com.suonk.oc_project4.ui.create;

import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

public class CreateMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    private SingleLiveEvent<ViewAction> singleEventAction = new SingleLiveEvent<>();

    public CreateMeetingViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public void createMeeting(@NonNull String subject,
                              @NonNull String place,
                              @NonNull String time,
                              @NonNull String listOfMails) {

        repository.addNewMeeting(subject, place, time, listOfMails);
    }

    public String convertChipGroupToListOfString(ChipGroup listOfMails) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < listOfMails.getChildCount(); i++) {
            String chip = ((Chip) listOfMails.getChildAt(i)).getText().toString();
            list.add(chip);
        }

        return list.toString();
    }

    public String convertTime(@NonNull String timeFrom,
                               @NonNull String timeTo) {
        return timeFrom + " to " + timeTo;
    }

    public Boolean checkIfTimeToSuperiorThanTimeFrom(@NonNull String timeFrom, @NonNull String timeTo) {
        int timeFromInt;
        int timeToInt;
        if (timeFrom.charAt(1) == 'h') {
            timeFromInt = Integer.parseInt(String.valueOf(timeFrom.charAt(0)));
        } else {
            timeFromInt = Integer.parseInt(String.valueOf(timeFrom.charAt(0)) + String.valueOf(timeFrom.charAt(1)));
        }
        if (timeTo.charAt(1) == 'h') {
            timeToInt = Integer.parseInt(String.valueOf(timeTo.charAt(0)));
        } else {
            timeToInt = Integer.parseInt(String.valueOf(timeTo.charAt(0)) + String.valueOf(timeTo.charAt(1)));
        }

        return timeFromInt < timeToInt;
    }

    public boolean checkIfFieldsNotEmpty(@NonNull String subject, @NonNull String timeFrom,
                                         @NonNull String timeTo,
                                         @NonNull String place) {
        return (!subject.isEmpty() && !timeFrom.isEmpty() && !timeTo.isEmpty() && !place.isEmpty());
    }

    public boolean checkIfEmailValid(@NonNull String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public List<String> convertChipGroupToList(@NonNull ChipGroup chipGroup) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            String chip = ((Chip) chipGroup.getChildAt(i)).getText().toString();
            list.add(chip);
        }

        return list;
    }

    public LiveData<ViewAction> getSingleEventActionLiveData() {
        return singleEventAction;
    }

    abstract static class ViewAction {
        static class PopStack extends ViewAction {

        }
    }
}
