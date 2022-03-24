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
    public void convertStartAndEndTimeToOneString() {
        assertEquals("4h30 to 6h45", viewModel.convertStartAndEndTimeToOneString("4h30", "6h45"));
        assertEquals("9h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("9h30", "12h45"));
        assertEquals("10h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("10h30", "12h45"));
        assertNotEquals("10h30 to 12h45", viewModel.convertStartAndEndTimeToOneString("10h31", "12h45"));
    }

    @Test
    public void checkIfFieldsNotEmpty() {
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "test", ""));
        assertTrue(viewModel.checkIfFieldsNotEmpty("test", "test", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "test", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", "test"));
        assertFalse(viewModel.checkIfFieldsNotEmpty("", "", ""));
        assertFalse(viewModel.checkIfFieldsNotEmpty("test", "", ""));
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
    public void checkIfTimeEndSuperiorThanTimeStart() {
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("4h30", "6h45"));
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("9h30", "12h45"));
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("10h30", "15h45"));
        assertTrue(viewModel.checkIfEndTimeSuperiorThanStartTime("15h30", "15h45"));
        assertFalse(viewModel.checkIfEndTimeSuperiorThanStartTime("15h55", "15h45"));
        assertFalse(viewModel.checkIfEndTimeSuperiorThanStartTime("15h45", "15h45"));
        assertFalse(viewModel.checkIfEndTimeSuperiorThanStartTime("19h45", "13h30"));
    }
}