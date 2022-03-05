package com.suonk.oc_project4.ui.create;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.list.MeetingsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class CreateMeetingViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private CreateMeetingViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new CreateMeetingViewModel(meetingRepository);
    }

    @Test
    public void addNewMeetingWithSuccess() {
        String listOfMails = "blablabla@gmail.com, 123@gmail.com";

        Meeting meeting = new Meeting(1, "Maths", "Sonic Heroes", "10h30 to 12h30", listOfMails);
        viewModel.createMeeting(meeting.getSubject(), meeting.getPlace(), meeting.getTime(), meeting.getListOfMails());

        assertTrue(meetingRepository.getAllMeetings().getValue().contains(meeting));
    }

    @Test
    public void checkIfEmailValid() {
        assertTrue(viewModel.checkIfEmailValid("test@test.com"));
        assertFalse(viewModel.checkIfEmailValid("test"));
    }

    @Test
    public void checkEditActionDone() {
    }
}