package com.suonk.oc_project4.ui.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.BDDMockito.given;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MeetingDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private MutableLiveData<Meeting> meetingMutableLiveData;

    private MeetingDetailsViewModel viewModel;

    @Before
    public void setup() {
        meetingMutableLiveData = new MutableLiveData<>();

        given(meetingRepository.getMeetingById(1)).willReturn(meetingMutableLiveData);

        viewModel = new MeetingDetailsViewModel(meetingRepository);

        meetingMutableLiveData.setValue(getDefaultMeeting("Maths", "Mario",
                "10h30 to 12h30"));
    }

    @Test
    public void nominalCase() {
        MeetingDetailsViewState meetingDetailsViewState = TestUtils.getValueForTesting(viewModel.getMeetingDetailsLiveData(1));

        assertEquals(getDefaultMeetingDetailsViewState("Maths", "10h30", "12h30", 1),
                meetingDetailsViewState);
    }

    @Test
    public void placeToArrayAdapterPosition() {
        assertEquals(0, viewModel.placeToArrayAdapterPosition(""));
        assertEquals(0, viewModel.placeToArrayAdapterPosition("Peach"));
        assertEquals(1, viewModel.placeToArrayAdapterPosition("Mario"));
        assertEquals(2, viewModel.placeToArrayAdapterPosition("Luigi"));
        assertEquals(3, viewModel.placeToArrayAdapterPosition("Bowser"));
        assertEquals(4, viewModel.placeToArrayAdapterPosition("Toad"));
        assertEquals(5, viewModel.placeToArrayAdapterPosition("Yoshi"));
        assertEquals(6, viewModel.placeToArrayAdapterPosition("Daisy"));
        assertEquals(7, viewModel.placeToArrayAdapterPosition("Donkey Kong"));

        assertNotEquals(3, viewModel.placeToArrayAdapterPosition("Peach"));
        assertNotEquals(4, viewModel.placeToArrayAdapterPosition("Mario"));
        assertNotEquals(5, viewModel.placeToArrayAdapterPosition("Luigi"));
        assertNotEquals(6, viewModel.placeToArrayAdapterPosition("Bowser"));
        assertNotEquals(7, viewModel.placeToArrayAdapterPosition("Toad"));
        assertNotEquals(0, viewModel.placeToArrayAdapterPosition("Yoshi"));
        assertNotEquals(1, viewModel.placeToArrayAdapterPosition("Daisy"));
        assertNotEquals(2, viewModel.placeToArrayAdapterPosition("Donkey Kong"));
    }

    @Test
    public void convertTimeToStartTime() {
        assertEquals("4h30", viewModel.convertTimeToStartTime("4h30 to 6h45"));
        assertEquals("9h00", viewModel.convertTimeToStartTime("9h00 to 12h45"));
        assertEquals("9h30", viewModel.convertTimeToStartTime("9h30 to 16h45"));
        assertEquals("12h45", viewModel.convertTimeToStartTime("12h45 to 21h45"));

        assertNotEquals("4h3", viewModel.convertTimeToStartTime("4h30 to 6h45"));
        assertNotEquals("9h01", viewModel.convertTimeToStartTime("9h00 to 12h45"));
        assertNotEquals("93h0", viewModel.convertTimeToStartTime("9h30 to 16h45"));
        assertNotEquals("12h.45", viewModel.convertTimeToStartTime("12h45 to 21h45"));
    }

    @Test
    public void convertTimeToEndTime() {
        assertEquals("6h45", viewModel.convertTimeToEndTime("4h30 to 6h45"));
        assertEquals("12h45", viewModel.convertTimeToEndTime("9h00 to 12h45"));
        assertEquals("16h45", viewModel.convertTimeToEndTime("9h30 to 16h45"));
        assertEquals("21h45", viewModel.convertTimeToEndTime("12h45 to 21h45"));

        assertNotEquals("6h 45", viewModel.convertTimeToEndTime("4h30 to 6h45"));
        assertNotEquals(" 12h45", viewModel.convertTimeToEndTime("9h00 to 12h45"));
        assertNotEquals("16h45.", viewModel.convertTimeToEndTime("9h30 to 16h45"));
        assertNotEquals("21h45 ", viewModel.convertTimeToEndTime("12h45 to 21h45"));
    }

    @NonNull
    private Meeting getDefaultMeeting(@NonNull String subject, @NonNull String place, @NonNull String time) {
        return new Meeting(1, subject, place, time, "blablabla@gmail.com, 123@gmail.com");
    }

    @NonNull
    private MeetingDetailsViewState getDefaultMeetingDetailsViewState(@NonNull String subject,
                                                                      @NonNull String startTime,
                                                                      @NonNull String endTime,
                                                                      int place) {
        return new MeetingDetailsViewState(subject, startTime, endTime, place, "blablabla@gmail.com, 123@gmail.com");
    }
}