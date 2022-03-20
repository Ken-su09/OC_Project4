package com.suonk.oc_project4.ui.list;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.data.meetings.Meeting;
import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.utils.TestUtils;

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

        viewModel = new MeetingsViewModel(meetingRepository);
        meetingsMutableLiveData.setValue(getDefaultMeetings());
    }

    @Test
    public void initialCase() {
        meetingsMutableLiveData.setValue(new ArrayList<>());
        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void nominalCase() {
        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(10, listOfMeetings.size());
        assertEquals(getDefaultViewStates(), listOfMeetings);
    }

    @Test
    public void get_meetings_filter_by_place_peach_no_filter_date_with_success() {
        viewModel.setFilterDateLiveData(R.id.no_filter_date);
        viewModel.setFilterPlaceLiveData(R.id.place_peach);

        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(3, listOfMeetings.size());
        assertEquals("Peach", listOfMeetings.get(0).getPlace());
        assertEquals("Peach", listOfMeetings.get(1).getPlace());
        assertEquals("Peach", listOfMeetings.get(2).getPlace());
    }

    @Test
    public void get_meetings_filter_by_date_under_8_no_filter_place_with_success() {
        viewModel.setFilterDateLiveData(R.id.date_under_8);
        viewModel.setFilterPlaceLiveData(R.id.no_filter_place);

        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(1, listOfMeetings.size());
        assertEquals("6h45 to 7h30", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_date_between_10_and_12_filter_place_by_mario_then_no_filter_date_with_success() {
        viewModel.setFilterDateLiveData(R.id.date_over_10_under_12);
        viewModel.setFilterPlaceLiveData(R.id.place_mario);

        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(1, listOfMeetings.size());
        assertEquals("10h30 to 12h30", listOfMeetings.get(0).getTime());
        assertEquals("Mario", listOfMeetings.get(0).getPlace());

        viewModel.setFilterDateLiveData(R.id.no_filter_date);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(1, listOfMeetings.size());
        assertEquals("10h30 to 12h30", listOfMeetings.get(0).getTime());
        assertEquals("Mario", listOfMeetings.get(0).getPlace());
    }

    @Test
    public void get_meetings_filter_by_place_bowser_should_be_empty() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_bowser);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_wrong_place() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(0);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_null_place_then_null_date() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(null);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(10, listOfMeetings.size());

        viewModel.setFilterDateLiveData(null);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(10, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_place_luigi_and_filter_date_between_8_and_10_with_success() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_luigi);
        viewModel.setFilterDateLiveData(R.id.date_over_8_under_10);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_place_toad_then_filter_by_date_between_14_16_with_success() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_toad);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(2, listOfMeetings.size());
        assertEquals("Toad", listOfMeetings.get(0).getPlace());
        assertEquals("Toad", listOfMeetings.get(1).getPlace());

        viewModel.setFilterDateLiveData(R.id.date_over_14_under_16);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Toad", listOfMeetings.get(0).getPlace());
        assertEquals("14h23 to 15h30", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_place_luigi_and_filter_by_date_between_14_16_should_be_empty_then_change_filter_date_to_8_10() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_luigi);
        viewModel.setFilterDateLiveData(R.id.date_over_14_under_16);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());

        viewModel.setFilterDateLiveData(R.id.date_over_8_under_10);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Luigi", listOfMeetings.get(0).getPlace());
        assertEquals("8h30 to 10h00", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_place_peach_filter_date_10_and_12_should_be_empty() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_peach);
        viewModel.setFilterDateLiveData(R.id.date_over_10_under_12);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_place_peach_filter_date_18_and_20_with_success() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_peach);
        viewModel.setFilterDateLiveData(R.id.date_over_18_under_20);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Peach", listOfMeetings.get(0).getPlace());
        assertEquals(18, viewModel.convertTimeStringToInt(listOfMeetings.get(0).getTime()));
        assertEquals("18h30 to 22h30", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_place_yoshi_filter_date_20_and_22_should_be_empty() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_yoshi);
        viewModel.setFilterDateLiveData(R.id.date_over_20_under_22);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
    }

    @Test
    public void get_meetings_filter_by_place_daisy_filter_date_22_and_00_should_be_empty_then_no_filter_place() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_daisy);
        viewModel.setFilterDateLiveData(R.id.date_over_22_under_00);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());

        viewModel.setFilterPlaceLiveData(R.id.no_filter_place);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Peach", listOfMeetings.get(0).getPlace());
        assertEquals(22, viewModel.convertTimeStringToInt(listOfMeetings.get(0).getTime()));
        assertEquals("22h12 to 23h30", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_date_between_10_and_12_filter_place_mario_with_success() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_mario);
        viewModel.setFilterDateLiveData(R.id.date_over_10_under_12);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Mario", listOfMeetings.get(0).getPlace());
        assertEquals(10, viewModel.convertTimeStringToInt(listOfMeetings.get(0).getTime()));
        assertEquals("10h30 to 12h30", listOfMeetings.get(0).getTime());
    }

    @Test
    public void get_meetings_filter_by_date_between_10_and_12_filter_place_mario_with_success_and_then_no_filter_both() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_mario);
        viewModel.setFilterDateLiveData(R.id.date_over_10_under_12);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Mario", listOfMeetings.get(0).getPlace());
        assertEquals(10, viewModel.convertTimeStringToInt(listOfMeetings.get(0).getTime()));
        assertEquals("10h30 to 12h30", listOfMeetings.get(0).getTime());

        viewModel.setFilterPlaceLiveData(R.id.no_filter_place);
        viewModel.setFilterDateLiveData(R.id.no_filter_date);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(10, listOfMeetings.size());
    }

    @Test
    public void get_meetings_filter_by_date_between_10_and_12_filter_place_mario_with_success_and_then_filter_place_peach_with_no_filter_date() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_mario);
        viewModel.setFilterDateLiveData(R.id.date_over_10_under_12);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Mario", listOfMeetings.get(0).getPlace());
        assertEquals(10, viewModel.convertTimeStringToInt(listOfMeetings.get(0).getTime()));
        assertEquals("10h30 to 12h30", listOfMeetings.get(0).getTime());

        viewModel.setFilterPlaceLiveData(R.id.place_peach);
        viewModel.setFilterDateLiveData(R.id.no_filter_date);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(3, listOfMeetings.size());
    }

    @Test
    public void filter_by_date_between_12_and_14_filter_place_donkey_kong_should_be_empty_then_no_filter_date_should_be_one_then_filter_date_16_to_18() {
        List<MeetingsViewState> listOfMeetings;

        viewModel.setFilterPlaceLiveData(R.id.place_donkey_kong);
        viewModel.setFilterDateLiveData(R.id.date_over_12_under_14);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertTrue(listOfMeetings.isEmpty());
        assertEquals(0, listOfMeetings.size());

        viewModel.setFilterDateLiveData(R.id.no_filter_date);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertFalse(listOfMeetings.isEmpty());
        assertEquals(1, listOfMeetings.size());
        assertEquals("Donkey Kong", listOfMeetings.get(0).getPlace());

        viewModel.setFilterDateLiveData(R.id.date_over_16_under_18);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(1, listOfMeetings.size());

        viewModel.setFilterPlaceLiveData(R.id.no_filter_place);

        listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());

        assertEquals(2, listOfMeetings.size());
    }

    @Test
    public void convert_time_to_int_with_success() {
        assertEquals(6, viewModel.convertTimeStringToInt("6h0 to 12h30"));
        assertEquals(9, viewModel.convertTimeStringToInt("9h30 to 12h30"));
        assertEquals(10, viewModel.convertTimeStringToInt("10h30 to 12h30"));
        assertEquals(12, viewModel.convertTimeStringToInt("12h30 to 18h30"));
        assertEquals(18, viewModel.convertTimeStringToInt("18h29 to 18h30"));
        assertNotEquals(19, viewModel.convertTimeStringToInt("18h29 to 19h30"));
    }

    @Test
    public void convert_time_to_start_time_with_success() {
        assertEquals("10h30", viewModel.convertTimeToStartTime("10h30 to 12h30"));
        assertEquals("12h30", viewModel.convertTimeToStartTime("12h30 to 18h30"));
        assertEquals("18h29", viewModel.convertTimeToStartTime("18h29 to 18h30"));
        assertNotEquals("19h30", viewModel.convertTimeToStartTime("18h29 to 19h30"));
        assertNotEquals("18h30", viewModel.convertTimeToStartTime("18h29 to 19h30"));
    }

    @Test
    public void onDeleteMeetingWithSuccess() {
        List<MeetingsViewState> listOfMeetings = TestUtils.getValueForTesting(viewModel.getAllMeetings());
        assertEquals(10, listOfMeetings.size());
        viewModel.onMeetingDelete(1);
//        assertEquals(1, listOfMeetings.size());
    }

    @NonNull
    private List<Meeting> getDefaultMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        meetings.add(new Meeting(1, "Subject1", "Peach", "18h30 to 22h30", "test@gmail.com"));
        meetings.add(new Meeting(2, "Maths", "Mario", "10h30 to 12h30", "blablabla@gmail.com, 123@gmail.com, 456@gmail.com"));
        meetings.add(new Meeting(3, "Subject3", "Luigi", "8h30 to 10h00", "test@gmail.com"));
        meetings.add(new Meeting(4, "Subject4", "Toad", "6h45 to 7h30", "test@gmail.com"));
        meetings.add(new Meeting(5, "Subject5", "Yoshi", "12h30 to 14h05", "test@gmail.com"));
        meetings.add(new Meeting(6, "Subject6", "Daisy", "16h35 to 18h30", "test@gmail.com"));
        meetings.add(new Meeting(7, "Subject7", "Donkey Kong", "16h35 to 18h30", "test@gmail.com"));
        meetings.add(new Meeting(8, "Subject8", "Peach", "20h12 to 21h30", "test@gmail.com"));
        meetings.add(new Meeting(9, "Subject9", "Peach", "22h12 to 23h30", "test@gmail.com"));
        meetings.add(new Meeting(10, "Subject10", "Toad", "14h23 to 15h30", "test@gmail.com"));


        return meetings;
    }

    @NonNull
    private List<MeetingsViewState> getDefaultViewStates() {
        List<MeetingsViewState> meetings = new ArrayList<>();

        meetings.add(new MeetingsViewState(1, "Subject1", "18h30 to 22h30", "Peach", "test@gmail.com"));
        meetings.add(new MeetingsViewState(2, "Maths", "10h30 to 12h30", "Mario", "blablabla@gmail.com, 123@gmail.com, 456@gmail.com"));
        meetings.add(new MeetingsViewState(3, "Subject3", "8h30 to 10h00", "Luigi", "test@gmail.com"));
        meetings.add(new MeetingsViewState(4, "Subject4", "6h45 to 7h30", "Toad", "test@gmail.com"));
        meetings.add(new MeetingsViewState(5, "Subject5", "12h30 to 14h05", "Yoshi", "test@gmail.com"));
        meetings.add(new MeetingsViewState(6, "Subject6", "16h35 to 18h30", "Daisy", "test@gmail.com"));
        meetings.add(new MeetingsViewState(7, "Subject7", "16h35 to 18h30", "Donkey Kong", "test@gmail.com"));
        meetings.add(new MeetingsViewState(8, "Subject8", "20h12 to 21h30", "Peach", "test@gmail.com"));
        meetings.add(new MeetingsViewState(9, "Subject9", "22h12 to 23h30", "Peach", "test@gmail.com"));
        meetings.add(new MeetingsViewState(10, "Subject10", "14h23 to 15h30", "Toad", "test@gmail.com"));

        return meetings;
    }
}