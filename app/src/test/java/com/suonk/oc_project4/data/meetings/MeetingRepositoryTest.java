package com.suonk.oc_project4.data.meetings;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

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
public class MeetingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository meetingRepository;

    @Before
    public void setUp() {
        meetingRepository = new MeetingRepository();
    }

    @Test
    public void addNewMeetingWithSuccess() {
        Meeting meeting = new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30",
                "blablabla@gmail.com");
        meetingRepository.addNewMeeting(meeting.getSubject(), meeting.getPlace(), meeting.getTime(),
                meeting.getListOfMails());

        meetingRepository.getAllMeetings().observeForever(meetings -> {
        });

        List<Meeting> listOfMeetings = meetingRepository.getAllMeetings().getValue();

        assert listOfMeetings != null;
        assertEquals(1, listOfMeetings.size());
        Meeting meetingFromList = listOfMeetings.get(0);
        assertEquals(meeting, meetingFromList);
    }

    @Test
    public void deleteMeeting() {
        Meeting meeting = new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30",
                "blablabla@gmail.com");
        meetingRepository.addNewMeeting(meeting.getSubject(), meeting.getPlace(), meeting.getTime(),
                meeting.getListOfMails());

        meetingRepository.getAllMeetings().observeForever(meetings -> {
        });

        List<Meeting> listOfMeetings = meetingRepository.getAllMeetings().getValue();

        assert listOfMeetings != null;
        assertEquals(1, listOfMeetings.size());
        assertFalse(listOfMeetings.isEmpty());
        meetingRepository.deleteMeeting(1);
        assertTrue(listOfMeetings.isEmpty());
    }

    @Test
    public void getAllMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        Meeting meeting = new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");
        meetings.add(meeting);

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("blablabla@gmail.com");
        listOfMails.add("123@gmail.com");
        listOfMails.add("456@gmail.com");
        listOfMails.add("789@gmail.com");
        String result = listOfMails.stream()
                .collect(Collectors.joining(", ", "", ""));
        Meeting meeting2 = new Meeting(2, "Maths", "Sonic Heroes", "10h30 to 12h30", result);
        meetings.add(meeting2);

        meetingRepository.addNewMeeting(meeting.getSubject(), meeting.getPlace(), meeting.getTime(),
                meeting.getListOfMails());

        meetingRepository.addNewMeeting(meeting2.getSubject(), meeting2.getPlace(), meeting2.getTime(),
                meeting2.getListOfMails());

        meetingRepository.getAllMeetings().observeForever(results -> {
        });

        List<Meeting> listOfMeetings = meetingRepository.getAllMeetings().getValue();

        assert listOfMeetings != null;
        assertEquals(2, listOfMeetings.size());

        Meeting meetingFromList = listOfMeetings.get(0);
        Meeting meetingFromList2 = listOfMeetings.get(1);
        assertEquals(meeting, meetingFromList);
        assertEquals(meeting2, meetingFromList2);
        assertEquals(meetings, listOfMeetings);
    }
}