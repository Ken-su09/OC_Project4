package com.suonk.oc_project4.ui.list;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class MeetingsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private MutableLiveData<List<Meeting>> meetingsMutableLiveData;

    private MeetingsViewModel viewModel;

    @Before
    public void setup() {
        meetingsMutableLiveData = new MutableLiveData<>();

        given(meetingRepository.getAllMeetings()).willReturn(meetingsMutableLiveData);
        meetingsMutableLiveData.setValue(getDefaultMeetings());

        viewModel = new MeetingsViewModel(meetingRepository);
    }

    @Test
    public void initialCase() {
        meetingsMutableLiveData.setValue(new ArrayList<>());
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertTrue(viewModel.getAllMeetings().getValue().isEmpty());
    }

    @Test
    public void nominalCase() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertEquals(getDefaultViewStates(), viewModel.getAllMeetings().getValue());
    }

    @Test
    public void getMeetingsWithSuccess() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertFalse(viewModel.getAllMeetings().getValue().isEmpty());
    }

    @NonNull
    private List<Meeting> getDefaultMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("test@gmail.com");
        meetings.add(new Meeting(1, "Subject1", "Peach", "10h30 to 12h30", listOfMails.toString()));

        List<String> listOfMails2 = new ArrayList<>();
        listOfMails2.add("blablabla@gmail.com");
        listOfMails2.add("123@gmail.com");
        listOfMails2.add("456@gmail.com");
        listOfMails2.add("789@gmail.com");
        String result = listOfMails2.stream()
                .collect(Collectors.joining(", ", "", ""));
        meetings.add(new Meeting(2, "Maths", "Sonic Heroes", "10h30 to 12h30", result));

        return meetings;
    }

    @NonNull
    private List<MeetingsViewState> getDefaultViewStates() {
        List<MeetingsViewState> meetings = new ArrayList<>();

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("test@gmail.com");
        meetings.add(new MeetingsViewState(1, "Subject1", "10h30 to 12h30", "Peach", listOfMails.toString()));

        List<String> listOfMails2 = new ArrayList<>();
        listOfMails2.add("blablabla@gmail.com");
        listOfMails2.add("123@gmail.com");
        listOfMails2.add("456@gmail.com");
        listOfMails2.add("789@gmail.com");
        meetings.add(new MeetingsViewState(2, "Maths", "10h30 to 12h30", "Sonic Heroes", listOfMails2.toString()));

        return meetings;
    }
}