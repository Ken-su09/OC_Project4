package com.suonk.oc_project4.ui.create;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.details.MeetingDetailsViewState;
import com.suonk.oc_project4.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    public void initialCase() {
        CreateMeetingViewState meetingViewState = TestUtils.getValueForTesting(viewModel.getCreateMeetingViewState());

        assertNotNull(meetingViewState);
        assertTrue(meetingViewState.getSubject().isEmpty());
        assertTrue(meetingViewState.getPlace().isEmpty());
        assertTrue(meetingViewState.getStartTime().isEmpty());
        assertTrue(meetingViewState.getEndTime().isEmpty());
        assertTrue(meetingViewState.getListOfMails().isEmpty());
    }

    @Test
    public void nominalCase() {
        viewModel.createMeeting("Maths", "Peach", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");
        CreateMeetingViewState meeting = new CreateMeetingViewState("Maths", "10h30",
                "12h30", "Peach", "blablabla@gmail.com, 123@gmail.com");

        CreateMeetingViewState meetingViewState = TestUtils.getValueForTesting(viewModel.getCreateMeetingViewState());

        assertNotNull(meetingViewState);
        assertFalse(meetingViewState.getSubject().isEmpty());
        assertFalse(meetingViewState.getPlace().isEmpty());
        assertFalse(meetingViewState.getStartTime().isEmpty());
        assertFalse(meetingViewState.getEndTime().isEmpty());
        assertFalse(meetingViewState.getListOfMails().isEmpty());

        assertEquals(meeting, meetingViewState);
    }

    @Test
    public void tryToCreateMeetingWithNullPlace() {
        viewModel.createMeeting("Maths", null, "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com");

        CreateMeetingViewState meetingViewState = TestUtils.getValueForTesting(viewModel.getCreateMeetingViewState());

        assertNotNull(meetingViewState);
        assertFalse(meetingViewState.getSubject().isEmpty());
        assertFalse(meetingViewState.getStartTime().isEmpty());
        assertFalse(meetingViewState.getEndTime().isEmpty());
        assertNull(meetingViewState.getPlace());
        assertFalse(meetingViewState.getListOfMails().isEmpty());
    }

    @Test
    public void convertStartAndEndTimeToOneString() {
        assertEquals("4h30 to 6h45", viewModel.convertStartAndEndTimeToOneString("4h30", "6h45"));
        assertEquals("9h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("9h30", "12h45"));
        assertEquals("10h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("10h30", "12h45"));
        assertNotEquals("10h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("10h31", "12h45"));
    }

    @Test
    public void checkIfFieldsNotEmpty() {
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "test", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "test", "test", ""));
        assertTrue(viewModel.checkIfFieldsNotEmpty("test", "test", "test", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "test", "test", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", "test", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", "", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "", "", "test"));

    }

    @Test
    public void checkIfEmailValid() {
        assertTrue(viewModel.checkIfEmailValid("test@test.com"));
        assertTrue(viewModel.checkIfEmailValid("test@.com"));
        assertTrue(viewModel.checkIfEmailValid("testa*'7é*é23@test.com"));
        assertTrue(viewModel.checkIfEmailValid("test@........"));
        assertFalse(viewModel.checkIfEmailValid("test"));
        assertFalse(viewModel.checkIfEmailValid("test@"));
        assertFalse(viewModel.checkIfEmailValid("@.com"));
        assertFalse(viewModel.checkIfEmailValid("test.com"));
    }

    @Test
    public void checkIfTimeToSuperiorThanTimeFrom() {
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("4h30", "6h45"));
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("9h30", "12h45"));
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("10h30", "15h45"));
        assertFalse(viewModel.checkIfEndTimeSuperiorThanStartTime("19h45", "13h30"));
    }
}