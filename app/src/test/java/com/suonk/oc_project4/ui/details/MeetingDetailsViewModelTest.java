package com.suonk.oc_project4.ui.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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
public class MeetingDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private MutableLiveData<List<Meeting>> meetingsMutableLiveData;

    private MeetingDetailsViewModel viewModel;

    @Before
    public void setup() {
        meetingsMutableLiveData = new MutableLiveData<>();

        given(meetingRepository.getAllMeetings()).willReturn(meetingsMutableLiveData);

        viewModel = new MeetingDetailsViewModel(meetingRepository);

        meetingsMutableLiveData.setValue(getDefaultMeetings("Maths", "Mario",
                "10h30 to 12h30"));
    }

    @Test
    public void initialCase() {
        meetingsMutableLiveData.setValue(new ArrayList<>());
        meetingsMutableLiveData.observeForever(meetingsViewState -> {
        });

        viewModel.getMeetingSelected(1);

        viewModel.getMeetingDetailsLiveData().observeForever(meetingsViewState -> {
        });

        assertTrue(meetingsMutableLiveData.getValue().isEmpty());
        assertNull(viewModel.getMeetingDetailsLiveData().getValue());
    }

    @Test
    public void nominalCase() {
        meetingsMutableLiveData.observeForever(meetingsViewState -> {
        });

        viewModel.getMeetingSelected(1);

        viewModel.getMeetingDetailsLiveData().observeForever(meetingsViewState -> {
        });

        assertFalse(meetingsMutableLiveData.getValue().isEmpty());
        assertNotNull(viewModel.getMeetingDetailsLiveData().getValue());
    }

    @Test
    public void meetingDetailsEqualsToDefaultViewState() {
        meetingsMutableLiveData.observeForever(meetingsViewState -> {
        });

        viewModel.getMeetingSelected(1);

        viewModel.getMeetingDetailsLiveData().observeForever(meetingsViewState -> {
        });

        assertEquals(getDefaultMeetingDetailsViewState("Maths", "Mario",
                "10h30 to 12h30"), viewModel.getMeetingDetailsLiveData().getValue());
    }

    @Test
    public void convertTimeToStartTime() {
        assertEquals("10h30", viewModel.convertTimeToStartTime("10h30 to 12h30"));
    }

    @Test
    public void convertTimeToEndTime() {
        assertEquals("12h30", viewModel.convertTimeToEndTime("10h30 to 12h30"));
    }

    @NonNull
    private List<Meeting> getDefaultMeetings(@NonNull String subject, @NonNull String place, @NonNull String time) {
        List<Meeting> meetings = new ArrayList<>();

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("blablabla@gmail.com");
        listOfMails.add("123@gmail.com");
        listOfMails.add("456@gmail.com");
        listOfMails.add("789@gmail.com");
        String result = listOfMails.stream()
                .collect(Collectors.joining(", ", "", ""));
        meetings.add(new Meeting(1, subject, place, time, result));

        return meetings;
    }

    @NonNull
    private MeetingDetailsViewState getDefaultMeetingDetailsViewState(@NonNull String subject, @NonNull String place,
                                                                      @NonNull String time) {
        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("blablabla@gmail.com");
        listOfMails.add("123@gmail.com");
        listOfMails.add("456@gmail.com");
        listOfMails.add("789@gmail.com");
        String result = listOfMails.stream()
                .collect(Collectors.joining(", ", "", ""));
        return new MeetingDetailsViewState(subject, place, viewModel.convertTimeToStartTime(time),
                viewModel.convertTimeToEndTime(time), result);
    }
}