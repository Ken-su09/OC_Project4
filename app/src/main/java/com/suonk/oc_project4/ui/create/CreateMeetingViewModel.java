package com.suonk.oc_project4.ui.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String convertTimeToStartTime(String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    public String convertTimeToEndTime(String time) {
        String[] parts = time.split("to ");

        return parts[1];
    }
}
