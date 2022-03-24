package com.suonk.oc_project4.data.meetings;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.ui.details.MeetingDetailsViewState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository implements DefaultMeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());
    private int id = 0;

    private final String[] places = {"Peach", "Mario", "Luigi", "Bowser", "Toad", "Yoshi", "Daisy",
            "Donkey Kong"};

    public boolean addNewMeeting(@NonNull String subject,
                                 int position,
                                 @NonNull String time,
                                 @NonNull String listOfMails) {
        List<Meeting> meetings = meetingsLiveData.getValue();
        setId();

        if (!meetings.isEmpty()) {
            for (Meeting meeting : meetings) {
                if (meeting.getPlace().equals(getPlaces(position))) {
                    if (!checkIfPlaceIsAlreadyUse(time, meeting.getTime())) {
                        return false;
                    }
                }
            }
        }

        meetings.add(new Meeting(id, subject, getPlaces(position), time, listOfMails));
        meetingsLiveData.setValue(meetings);

        return true;
    }

    public void deleteMeeting(long id) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings != null) {
            for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
                Meeting meeting = iterator.next();
                if (meeting.getId() == id) {
                    iterator.remove();
                    break;
                }
            }
        }

        meetingsLiveData.setValue(meetings);
    }

    @NonNull
    public LiveData<List<Meeting>> getAllMeetings() {
        return meetingsLiveData;
    }

    @NonNull
    public String getPlaces(int position) {
        return places[position];
    }

    public int getPosition(String place) {
        for (int i = 0; i < places.length; i++) {
            if (place.equals(places[i])) {
                return i;
            }
        }
        return 0;
    }

    @NonNull
    public LiveData<Meeting> getMeetingById(long id) {
        MutableLiveData<Meeting> result = new MutableLiveData<>();
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings != null) {
            for (Meeting meeting : meetings) {
                if (meeting.getId() == id) {
                    result.setValue(meeting);
                    break;
                }
            }
        }

        return result;
    }

    public void setId() {
        id += 1;
    }

    private Boolean checkIfPlaceIsAlreadyUse(String inputTime, String timeToTest) {
        // inputTimeStart : 9
        int inputTimeStartHour = Integer.parseInt(convertTimeToStartTime(inputTime).split("h")[0]);
        // inputTimeStart : 30
        int inputTimeStartMinutes = Integer.parseInt(convertTimeToStartTime(inputTime).split("h")[1]);

        // inputTimeStart : 10
        int inputTimeEndHour = Integer.parseInt(convertTimeToEndTime(inputTime).split("h")[0]);
        // inputTimeStart : 15
        int inputTimeEndMinutes = Integer.parseInt(convertTimeToEndTime(inputTime).split("h")[1]);

        // inputTime : 9h30 to 10h15

        // inputTimeStart : 9

        int timeToTestStartHour = Integer.parseInt(convertTimeToStartTime(timeToTest).split("h")[0]);
        // inputTimeStart : 30
        int timeToTestStartMinutes = Integer.parseInt(convertTimeToStartTime(timeToTest).split("h")[1]);

        // inputTimeStart : 10
        int timeToTestEndHour = Integer.parseInt(convertTimeToEndTime(timeToTest).split("h")[0]);
        // inputTimeStart : 15
        int timeToTestEndMinutes = Integer.parseInt(convertTimeToEndTime(timeToTest).split("h")[1]);

        if (inputTimeStartHour == timeToTestStartHour) {
            if (inputTimeStartMinutes == timeToTestStartMinutes) {
                return false;
            } else if (inputTimeStartMinutes > timeToTestStartMinutes) {
                if (inputTimeStartHour < timeToTestEndHour) {
                    return false;
                } else if (inputTimeStartHour == timeToTestEndHour) {
                    if (inputTimeStartMinutes < timeToTestEndMinutes) {
                        return false;
                    } else if (inputTimeStartMinutes >= timeToTestEndMinutes) {
                        return true;
                    }
                }
            } else {
                if (inputTimeEndHour == timeToTestStartHour) {
                    return inputTimeEndMinutes <= timeToTestStartMinutes;
                } else return inputTimeEndHour <= timeToTestStartHour;
            }
        } else if (inputTimeStartHour > timeToTestStartHour) {
            if (inputTimeStartHour < timeToTestEndHour) {
                return false;
            } else if (inputTimeStartHour == timeToTestEndHour) {
                return inputTimeStartMinutes >= timeToTestEndMinutes;
            } else {
                return true;
            }
        } else {
            if (inputTimeEndHour == timeToTestStartHour) {
                return inputTimeEndMinutes <= timeToTestStartMinutes;
            } else return inputTimeEndHour < timeToTestStartHour;
        }

        return true;
    }

    private String convertTimeToStartTime(@NonNull String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    private String convertTimeToEndTime(@NonNull String time) {
        String[] parts = time.split("to ");

        return parts[1];
    }
}
