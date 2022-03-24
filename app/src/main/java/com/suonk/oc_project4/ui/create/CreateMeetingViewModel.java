package com.suonk.oc_project4.ui.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;

    public CreateMeetingViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    public boolean createMeeting(@NonNull String subject, int position, @NonNull String time,
                                 @NonNull String listOfMails) {
        return repository.addNewMeeting(subject, position, time, listOfMails);
    }

    public String convertStartAndEndTimeToOneString(@NonNull String startTime, @NonNull String endTime) {
        return startTime + " to " + endTime;
    }

    public Boolean checkIfEndTimeSuperiorThanStartTime(@NonNull String startTime, @NonNull String endTime) {
        int inputTimeStartHour = Integer.parseInt(startTime.split("h")[0]);
        int inputTimeStartMinutes = Integer.parseInt(startTime.split("h")[1]);

        int inputTimeEndHour = Integer.parseInt(endTime.split("h")[0]);
        int inputTimeEndMinutes = Integer.parseInt(endTime.split("h")[1]);

        if (inputTimeStartHour > inputTimeEndHour) {
            return false;
        } else if (inputTimeStartHour == inputTimeEndHour) {
            return inputTimeStartMinutes < inputTimeEndMinutes;
        }

        return true;
    }

    public boolean checkIfFieldsNotEmpty(@NonNull String subject, @NonNull String startTime,
                                         @NonNull String endTime) {
        return (!subject.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty());
    }

    public boolean checkIfEmailValid(@NonNull String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
