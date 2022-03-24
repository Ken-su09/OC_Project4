package com.suonk.oc_project4.data.meetings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(
                Collections.singletonList(new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com")),
                listOfMeetings
        );
    }

    @Test
    public void incrementationIdWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "listOfmail@test.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Maths", "Mario", "10h30 to 12h30", "listOfmail@test.com")
                ),
                listOfMeetings
        );
    }

    @Test
    public void deleteMeetingWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30",
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
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(2, listOfMeetings.size());

        meetingRepository.deleteMeeting(2);

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
    }

    @Test
    public void addTwoMeetingsAndDeleteTwoMeetings() {
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(2, listOfMeetings.size());

        meetingRepository.deleteMeeting(1);
        meetingRepository.deleteMeeting(2);

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void addTwoMeetingsWithSamePlaceAndDifferentTime() {
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 1, "12h30 to 18h30", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com"));
    }

    @Test
    public void add_meeting_same_place_time_overlaps_same_start_hour_and_end_hour() {
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 1, "12h30 to 18h30", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Maths Dev", 1, "12h30 to 18h30", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Maths", 1, "12h45 to 18h30", "blablabla@gmail.com"));
    }

    @Test
    public void add_meeting_same_place_time_overlaps_same_hour_different_minutes() {
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 2, "13h00 to 13h45", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Maths Dev", 2, "13h20 to 18h30", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Maths", 2, "13h45 to 18h30", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Maths", 2, "18h35 to 21h30", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 2, "21h30 to 21h45", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 2, "21h35 to 21h40", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 2, "21h45 to 21h50", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 2, "21h51 to 21h55", "blablabla@gmail.com"));


        assertTrue(meetingRepository.addNewMeeting("Android Dev", 3, "13h30 to 13h45", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 3, "13h15 to 18h30", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 3, "13h00 to 13h20", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 3, "13h20 to 13h30", "blablabla@gmail.com"));

        assertTrue(meetingRepository.addNewMeeting("Android Dev", 4, "13h20 to 15h30", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 4, "14h20 to 14h30", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 4, "15h20 to 15h40", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 4, "15h30 to 15h40", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 4, "15h45 to 15h50", "blablabla@gmail.com"));
    }

    @Test
    public void add_meeting_same_place_time_overlaps_input_time_end_hour_same_as_time_to_test_start_hour() {
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 5, "13h30 to 18h45", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 5, "10h30 to 13h45", "blablabla@gmail.com"));
        assertTrue(meetingRepository.addNewMeeting("Android Dev", 5, "10h30 to 13h30", "blablabla@gmail.com"));
        assertFalse(meetingRepository.addNewMeeting("Android Dev", 5, "9h30 to 10h31", "blablabla@gmail.com"));
    }

    @Test
    public void getPosition() {
        assertEquals(0, meetingRepository.getPosition("Peach"));
        assertEquals(1, meetingRepository.getPosition("Mario"));
        assertEquals(2, meetingRepository.getPosition("Luigi"));
        assertEquals(3, meetingRepository.getPosition("Bowser"));
        assertEquals(4, meetingRepository.getPosition("Toad"));
        assertEquals(5, meetingRepository.getPosition("Yoshi"));
        assertEquals(6, meetingRepository.getPosition("Daisy"));
        assertEquals(7, meetingRepository.getPosition("Donkey Kong"));
        assertEquals(0, meetingRepository.getPosition("doqz idjopifj ofj qepofieqjnf oef opeqjf qmoeifj"));
    }

    @Test
    public void removeRandomIdDoNotRemoveExistingMeeting() {
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30",
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
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        List<Meeting> listOfMeetings = TestUtils.getValueForTesting(meetingRepository.getAllMeetings());

        assertEquals(2, listOfMeetings.size());

        assertEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Maths", "Mario", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com")),
                listOfMeetings
        );

        assertNotEquals(
                Arrays.asList(
                        new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                        new Meeting(2, "Math", "Mario", "10h30 to 12h3", "blablabla@gmail.com, 12@gmail.com")),
                listOfMeetings
        );
    }

    @Test
    public void getMeetingByIdWithSuccess() {
        meetingRepository.addNewMeeting("Android Dev", 2, "12h30 to 18h30", "blablabla@gmail.com");
        meetingRepository.addNewMeeting("Maths", 1, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        Meeting meeting1 = TestUtils.getValueForTesting(meetingRepository.getMeetingById(1));

        assertEquals(
                new Meeting(1, "Android Dev", "Luigi", "12h30 to 18h30", "blablabla@gmail.com"),
                meeting1
        );

        Meeting meeting2 = TestUtils.getValueForTesting(meetingRepository.getMeetingById(2));

        assertEquals(
                new Meeting(2, "Maths", "Mario", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com"),
                meeting2
        );

        assertNotEquals(
                new Meeting(2, "Maths", "Mario", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com"),
                meeting1
        );
    }
}