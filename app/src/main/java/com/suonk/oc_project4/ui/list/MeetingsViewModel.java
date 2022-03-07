package com.suonk.oc_project4.ui.list;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingsViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository repository;
    private final MediatorLiveData<List<MeetingsViewState>> viewStateLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Integer> sortingModeLiveData = new MutableLiveData<>();

    @SuppressLint("NonConstantResourceId")
    public MeetingsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;

        LiveData<List<Meeting>> meetingsLiveData = repository.getAllMeetings();

        viewStateLiveData.addSource(meetingsLiveData, meetings -> {
            combine(meetings, sortingModeLiveData.getValue());
        });

        viewStateLiveData.addSource(sortingModeLiveData, sortingMode -> {
            combine(meetingsLiveData.getValue(), sortingMode);
        });

    }

    private void combine(@Nullable List<Meeting> meetings, @Nullable Integer sortingMode) {
        if (meetings == null) {
            return;
        }

        List<MeetingsViewState> meetingsViewStates = new ArrayList<>();

        for (Meeting meeting : meetings) {
            meetingsViewStates.add(
                new MeetingsViewState(
                    meeting.getId(),
                    meeting.getSubject(),
                    meeting.getTime(),
                    meeting.getPlace(),
                    meeting.getListOfMails()
                )
            );
        }

        List<MeetingsViewState> meetingsFiltered = new ArrayList<>();

        if (sortingMode != null) {
            for (MeetingsViewState meetingViewState : meetingsViewStates) {
                int meetingTime = convertTimeStringToInt(convertTimeToStartTime(meetingViewState.getTime()));
                Log.i("meetingTime", "" + meetingTime);
                switch (sortingMode) {
                    case 8:
                        if (meetingTime < 8) {
                            Log.i("meetingTime", "add");
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 10:
                        if (8 <= meetingTime && meetingTime < 10) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 12:
                        if (10 <= meetingTime && meetingTime < 12) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 14:
                        if (12 <= meetingTime && meetingTime < 14) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 16:
                        if (14 <= meetingTime && meetingTime < 16) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 18:
                        if (16 <= meetingTime && meetingTime < 18) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 20:
                        if (18 <= meetingTime && meetingTime < 20) {
                            meetingsFiltered.add(meetingViewState);
                        }
                    case 22:
                        if (20 <= meetingTime && meetingTime < 22) {
                            meetingsFiltered.add(meetingViewState);
                        }
                }
            }
        } else {
            meetingsFiltered.addAll(meetingsViewStates);
        }

        viewStateLiveData.setValue(meetingsFiltered);
    }

    public LiveData<List<MeetingsViewState>> getAllMeetings() {
        return viewStateLiveData;
    }

    private List<MeetingsViewState> filterMeetingsWithPlace(List<MeetingsViewState> meetings, String filterName) {
        List<MeetingsViewState> meetingsFilter = new ArrayList<>();
        for (MeetingsViewState meeting : meetings) {
            if (meeting.getPlace().equalsIgnoreCase(filterName)) {
                meetingsFilter.add(meeting);
            }
        }

        return meetingsFilter;
    }

    private List<MeetingsViewState> returnFilterMeetings(List<MeetingsViewState> meetings) {
        if (meetings != null) {
            isFilterLiveData.setValue(true);
            switch (idLiveData.getValue()) {
                case R.id.date_under_8:
                    return filterMeetingsWithDate(meetings, 8);
                case R.id.date_over_8_under_10:
                    return filterMeetingsWithDate(meetings, 10);
                case R.id.date_over_10_under_12:
                    return filterMeetingsWithDate(meetings, 12);
                case R.id.date_over_12_under_14:
                    return filterMeetingsWithDate(meetings, 14);
                case R.id.date_over_14_under_16:
                    return filterMeetingsWithDate(meetings, 16);
                case R.id.date_over_16_under_18:
                    return filterMeetingsWithDate(meetings, 18);
                case R.id.date_over_18_under_20:
                    return filterMeetingsWithDate(meetings, 20);
                case R.id.date_over_20_under_22:
                    return filterMeetingsWithDate(meetings, 22);
                case R.id.date_over_22_under_00:
                    return filterMeetingsWithDate(meetings, 24);
                case R.id.place_peach:
                    return filterMeetingsWithPlace(meetings, "Peach");
                case R.id.place_mario:
                    return filterMeetingsWithPlace(meetings, "Mario");
                case R.id.place_luigi:
                    return filterMeetingsWithPlace(meetings, "Luigi");
                case R.id.place_bowser:
                    return filterMeetingsWithPlace(meetings, "Bowser");
                case R.id.place_toad:
                    return filterMeetingsWithPlace(meetings, "Toad");
                case R.id.place_yoshi:
                    return filterMeetingsWithPlace(meetings, "Yoshi");
                case R.id.place_daisy:
                    return filterMeetingsWithPlace(meetings, "Daisy");
                case R.id.place_donkey_kong:
                    return filterMeetingsWithPlace(meetings, "Donkey Kong");
                default:
                    isFilterLiveData.setValue(false);
                    return meetings;
            }
        } else {
            return null;
        }
    }

    public void onMeetingDelete(long id) {
        repository.deleteMeeting(id);
    }

    private String convertTimeToStartTime(String time) {
        String[] parts = time.split(" to");

        return parts[0];
    }

    public int convertTimeStringToInt(@NonNull String time) {
        int timeInt;
        if (time.charAt(1) == 'h') {
            timeInt = Integer.parseInt(String.valueOf(time.charAt(0)));
        } else {
            timeInt = Integer.parseInt(String.valueOf(time.charAt(0)) + String.valueOf(time.charAt(1)));
        }

        return timeInt;
    }
}