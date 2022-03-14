package com.suonk.oc_project4.data.meetings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.suonk.oc_project4.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void initialCase() {
        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void addNewMeetingWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(
                Collections.singletonList(new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com")),
                listOfMeetings
        );
    }

    @Test
    public void incrementationIdWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", "Sonic Heroes", "10h30 to 12h30", "listOfmail@test.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Maths", "Sonic Heroes", "10h30 to 12h30", "listOfmail@test.com")
                ),
                listOfMeetings
        );
    }

    @Test
    public void deleteMeetingWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30",
                "blablabla@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(1, listOfMeetings.size());
        assertFalse(listOfMeetings.isEmpty());
        meetingRepository.deleteMeeting(1);
        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void addTwoMeetingsAndDeleteOneMeeting() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", "Sonic Heroes", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(2, listOfMeetings.size());

        meetingRepository.deleteMeeting(2);

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
    }

    @Test
    public void addTwoMeetingsAndDeleteTwoMeetings() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", "Sonic Heroes", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(2, listOfMeetings.size());

        meetingRepository.deleteMeeting(1);
        meetingRepository.deleteMeeting(2);

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void removeRandomIdDoNotRemoveExistingMeeting() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30",
                "blablabla@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(1, listOfMeetings.size());
        assertFalse(listOfMeetings.isEmpty());
        meetingRepository.deleteMeeting(1132);
        assertEquals(1, listOfMeetings.size());
        assertFalse(listOfMeetings.isEmpty());
        assertEquals(
                Collections.singletonList(new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com")),
                listOfMeetings
        );
    }

    @Test
    public void getAllMeetingsWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", "Sonic Heroes", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(2, listOfMeetings.size());

        assertEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Maths", "Sonic Heroes", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com")),
                listOfMeetings
        );

        assertNotEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Math", "Sonic Heroes", "10h30 to 12h3", "blablabla@gmail.com, 12@gmail.com")),
                listOfMeetings
        );
    }
}