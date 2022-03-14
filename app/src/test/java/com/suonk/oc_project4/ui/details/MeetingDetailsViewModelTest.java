package com.suonk.oc_project4.ui.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.list.MeetingsViewState;
import com.suonk.oc_project4.utils.TestUtils;

import org.junit.Assert;
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

        meetingsMutableLiveData.setValue(getDefaultMeeting("Maths", "Mario",
                "10h30 to 12h30"));
    }

    @Test
    public void initialCase() {
        meetingsMutableLiveData.setValue(new ArrayList<>());
        viewModel.getMeetingSelected(1);

        viewModel.getMeetingDetailsLiveData().observeForever(meetingsViewState -> {
            assertTrue(meetingsMutableLiveData.getValue().isEmpty());
            assertNull(meetingsViewState);
        });
    }

    @Test
    public void nominalCase() {
        viewModel.getMeetingSelected(1);
        MeetingDetailsViewState meetingDetailsViewState = TestUtils.getValueForTesting(viewModel.getMeetingDetailsLiveData());

        assertFalse(meetingsMutableLiveData.getValue().isEmpty());
        assertNotNull(meetingDetailsViewState);
        assertEquals(getDefaultMeetingDetailsViewState("Maths", "Mario", "10h30 to 12h30"),
                meetingDetailsViewState);
    }

    @Test
    public void getMeetingDetailsWithWrongId() {
        viewModel.getMeetingSelected(219);

        viewModel.getMeetingDetailsLiveData().observeForever(Assert::assertNull);
    }

    @Test
    public void placeToArrayAdapterPositionWrongPlace() {
        assertEquals(0, viewModel.placeToArrayAdapterPosition("blablabla"));
        assertNotEquals(1, viewModel.placeToArrayAdapterPosition("blablabla"));
    }

    @Test
    public void placeToArrayAdapterPositionAllPlaces() {
        assertEquals(0, viewModel.placeToArrayAdapterPosition("Peach"));
        assertEquals(1, viewModel.placeToArrayAdapterPosition("Mario"));
        assertEquals(2, viewModel.placeToArrayAdapterPosition("Luigi"));
        assertEquals(3, viewModel.placeToArrayAdapterPosition("Bowser"));
        assertEquals(4, viewModel.placeToArrayAdapterPosition("Toad"));
        assertEquals(5, viewModel.placeToArrayAdapterPosition("Yoshi"));
        assertEquals(6, viewModel.placeToArrayAdapterPosition("Daisy"));
        assertEquals(7, viewModel.placeToArrayAdapterPosition("Donkey Kong"));
    }

    @Test
    public void convertTimeToStartTime() {
        assertEquals("10h30", viewModel.convertTimeToStartTime("10h30 to 12h30"));
        assertEquals("12h30", viewModel.convertTimeToStartTime("12h30 to 18h30"));
        assertEquals("18h30", viewModel.convertTimeToStartTime("18h30 to 21h30"));
        assertNotEquals("10h30", viewModel.convertTimeToStartTime("10h29 to 12h30"));
        assertNotEquals("12h30", viewModel.convertTimeToStartTime("10h29 to 12h30"));
    }

    @Test
    public void convertTimeToEndTime() {
        assertEquals("12h30", viewModel.convertTimeToEndTime("10h30 to 12h30"));
        assertEquals("18h30", viewModel.convertTimeToEndTime("12h30 to 18h30"));
        assertEquals("21h30", viewModel.convertTimeToEndTime("18h30 to 21h30"));
        assertNotEquals("12h30", viewModel.convertTimeToEndTime("10h29 to 12h29"));
        assertNotEquals("10h29", viewModel.convertTimeToEndTime("10h29 to 12h29"));
    }

    @NonNull
    private List<Meeting> getDefaultMeeting(@NonNull String subject, @NonNull String place, @NonNull String time) {
        List<Meeting> meetings = new ArrayList<>();

        meetings.add(new Meeting(1, subject, place, time, "blablabla@gmail.com, 123@gmail.com"));

        return meetings;
    }

    @NonNull
    private MeetingDetailsViewState getDefaultMeetingDetailsViewState(@NonNull String subject, @NonNull String place,
                                                                      @NonNull String time) {
        return new MeetingDetailsViewState(subject, place, viewModel.convertTimeToStartTime(time),
                viewModel.convertTimeToEndTime(time), "blablabla@gmail.com, 123@gmail.com");
    }
}