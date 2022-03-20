package com.suonk.oc_project4.ui.list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
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

    private final MutableLiveData<Integer> filterPlaceLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> filterDateLiveData = new MutableLiveData<>();

    public MeetingsViewModel(@NonNull MeetingRepository repository) {
        this.repository = repository;

        LiveData<List<Meeting>> meetingsLiveData = repository.getAllMeetings();

        viewStateLiveData.addSource(meetingsLiveData, meetings -> {
            combine(meetings, filterPlaceLiveData.getValue(), filterDateLiveData.getValue());
        });

        viewStateLiveData.addSource(filterPlaceLiveData, filterPlace -> {
            combine(meetingsLiveData.getValue(), filterPlace, filterDateLiveData.getValue());
        });

        viewStateLiveData.addSource(filterDateLiveData, filterDate -> {
            combine(meetingsLiveData.getValue(), filterPlaceLiveData.getValue(), filterDate);
        });
    }

    private void combine(@Nullable List<Meeting> meetings, @Nullable Integer filterPlace,
                         @Nullable Integer filterDate) {
        if (meetings == null) {
            return;
        }

        if (filterDate != null) {
            if (filterDate == R.id.no_filter_date) {
                filterDateLiveData.setValue(null);
                filterDate = null;
            }
        }

        if (filterPlace != null) {
            if (filterPlace == R.id.no_filter_place) {
                filterPlaceLiveData.setValue(null);
                filterPlace = null;
            }
        }

        List<MeetingsViewState> meetingsViewStates = new ArrayList<>();
        List<MeetingsViewState> meetingsFiltered = new ArrayList<>();
        List<MeetingsViewState> meetingsFilteredByDate = new ArrayList<>();
        List<MeetingsViewState> meetingsFilteredByPlace = new ArrayList<>();

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

        // x under 8
        if (filterDate != null) {
            if (!getMeetingsFilteredByDate(meetingsViewStates, filterDate).isEmpty()) {
                if (!meetingsFilteredByDate.containsAll(getMeetingsFilteredByDate(meetingsViewStates, filterDate))) {
                    meetingsFilteredByDate.addAll(getMeetingsFilteredByDate(meetingsViewStates, filterDate));
                }
            }
        }

        // Peach
        if (filterPlace != null) {
            if (!filterMeetingsWithPlace(meetingsViewStates, filterPlace).isEmpty()) {
                if (!meetingsFilteredByPlace.containsAll(filterMeetingsWithPlace(meetingsViewStates, filterPlace))) {
                    meetingsFilteredByPlace.addAll(filterMeetingsWithPlace(meetingsViewStates, filterPlace));
                }

                // 3 x Peach
                if (filterDate != null) {
                    if (filterMeetingsWithPlace(meetingsFilteredByDate, filterPlace).isEmpty()) {
                        meetingsFilteredByPlace.clear();
                        meetingsFilteredByDate.clear();
                    } else {
                        meetingsFilteredByPlace.clear();
                        meetingsFilteredByPlace.addAll(filterMeetingsWithPlace(meetingsFilteredByDate, filterPlace));
                        meetingsFilteredByDate.clear();
                    }
                }
            } else {
                meetingsFilteredByDate.clear();
            }
        }

        if (filterDate == null && filterPlace == null) {
            meetingsFiltered.addAll(meetingsViewStates);
        } else {
            Log.i("testFilter", "3 : " + meetingsFilteredByDate.isEmpty());
            if (!meetingsFilteredByDate.isEmpty()) {
                for (MeetingsViewState meeting : meetingsFilteredByDate) {
                    if (!meetingsFiltered.contains(meeting)) {
                        meetingsFiltered.add(meeting);
                    }
                }
            }

            Log.i("testFilter", "4 : " + meetingsFilteredByPlace.isEmpty());
            if (!meetingsFilteredByPlace.isEmpty()) {
                for (MeetingsViewState meeting : meetingsFilteredByPlace) {
                    if (!meetingsFiltered.contains(meeting)) {
                        meetingsFiltered.add(meeting);
                    }
                }
            }
        }

        viewStateLiveData.setValue(meetingsFiltered);
    }

    public LiveData<List<MeetingsViewState>> getAllMeetings() {
        return viewStateLiveData;
    }

    //region =========================================== FILTERS ============================================

    public void setFilterPlaceLiveData(@NonNull Integer filterPlace) {
        filterPlaceLiveData.setValue(filterPlace);
    }

    public void setFilterDateLiveData(@NonNull Integer filterDate) {
        filterDateLiveData.setValue(filterDate);
    }

    @NonNull
    private List<MeetingsViewState> getMeetingsFilteredByPlace(@NonNull List<MeetingsViewState> meetings, String filterName) {
        List<MeetingsViewState> meetingsFilter = new ArrayList<>();
        for (MeetingsViewState meeting : meetings) {
            if (meeting.getPlace().equalsIgnoreCase(filterName)) {
                meetingsFilter.add(meeting);
            }
        }
        return meetingsFilter;
    }

    @NonNull
    private List<MeetingsViewState> filterMeetingsWithPlace(List<MeetingsViewState> meetings, @NonNull Integer filterPlace) {
        if (filterPlace == R.id.place_peach) {
            return getMeetingsFilteredByPlace(meetings, "Peach");
        } else if (filterPlace == R.id.place_mario) {
            return getMeetingsFilteredByPlace(meetings, "Mario");

        } else if (filterPlace == R.id.place_luigi) {
            return getMeetingsFilteredByPlace(meetings, "Luigi");

        } else if (filterPlace == R.id.place_bowser) {
            return getMeetingsFilteredByPlace(meetings, "Bowser");

        } else if (filterPlace == R.id.place_toad) {
            return getMeetingsFilteredByPlace(meetings, "Toad");

        } else if (filterPlace == R.id.place_yoshi) {
            return getMeetingsFilteredByPlace(meetings, "Yoshi");

        } else if (filterPlace == R.id.place_daisy) {
            return getMeetingsFilteredByPlace(meetings, "Daisy");

        } else if (filterPlace == R.id.place_donkey_kong) {
            return getMeetingsFilteredByPlace(meetings, "Donkey Kong");
        } else {
            return getMeetingsFilteredByPlace(meetings, "");
        }
    }

    @NonNull
    private List<MeetingsViewState> getMeetingsFilteredByDate(@NonNull List<MeetingsViewState> meetings, Integer filterDate) {
        List<MeetingsViewState> meetingsFilter = new ArrayList<>();
        for (MeetingsViewState meeting : meetings) {
            int meetingTime = convertTimeStringToInt(convertTimeToStartTime(meeting.getTime()));

            if (filterDate == R.id.date_under_8) {
                if (meetingTime < 8) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_8_under_10) {
                if (8 <= meetingTime && meetingTime < 10) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_10_under_12) {
                if (10 <= meetingTime && meetingTime < 12) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_12_under_14) {
                if (12 <= meetingTime && meetingTime < 14) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_14_under_16) {
                if (14 <= meetingTime && meetingTime < 16) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_16_under_18) {
                if (16 <= meetingTime && meetingTime < 18) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_18_under_20) {
                if (18 <= meetingTime && meetingTime < 20) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_20_under_22) {
                if (20 <= meetingTime && meetingTime < 22) {
                    meetingsFilter.add(meeting);
                }
            } else if (filterDate == R.id.date_over_22_under_00) {
                if (22 <= meetingTime && meetingTime < 24) {
                    meetingsFilter.add(meeting);
                }
            } else {
                break;
            }
        }

        return meetingsFilter;
    }

    //endregion

    public void onMeetingDelete(long id) {
        repository.deleteMeeting(id);
    }

    public String convertTimeToStartTime(@NonNull String time) {
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