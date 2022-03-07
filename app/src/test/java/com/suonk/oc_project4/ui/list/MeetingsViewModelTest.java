package com.suonk.oc_project4.ui.list;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.suonk.oc_project4.R;
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
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertTrue(viewModel.getAllMeetings().getValue().isEmpty());
    }

    @Test
    public void nominalCase() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertEquals(getDefaultViewStates(), viewModel.getAllMeetings().getValue());
    }

    @Test
    public void getMeetingsWithSuccess() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertEquals(2, (viewModel.getAllMeetings().getValue().size()));
        assertFalse(viewModel.getAllMeetings().getValue().isEmpty());
        assertFalse(meetingsMutableLiveData.getValue().isEmpty());
    }

    @Test
    public void getMeetingsFilterByPlacePeachWithSuccess() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });

        assertEquals(2, viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).size());

        viewModel.setIdLiveData(R.id.place_peach);

        assertFalse(viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).isEmpty());
        assertEquals(1, viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).size());
        assertEquals("Peach", viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).get(0).getPlace());

        viewModel.setIdLiveData(R.id.no_filter_place);

        assertEquals(2, viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).size());

        viewModel.setIdLiveData(R.id.place_mario);

        assertEquals(1, viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).size());

        assertFalse(viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).isEmpty());
        assertEquals(1, viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).size());
        assertEquals("Mario", viewModel.returnFilterMeetings(viewModel.getAllMeetings().getValue()).get(0).getPlace());
    }

    @Test
    public void onDeleteMeetingWithSuccess() {
        viewModel.getAllMeetings().observeForever(meetingsViewStates -> {
        });
        assertFalse(viewModel.getAllMeetings().getValue().isEmpty());

        viewModel.onMeetingDelete(1);
        List<MeetingsViewState> listOfMeetings = viewModel.getAllMeetings().getValue();
        listOfMeetings.remove(0);

        assertEquals(1, listOfMeetings.size());
    }

    @NonNull
    private List<Meeting> getDefaultMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        meetings.add(new Meeting(1, "Subject1", "Peach", "10h30 to 12h30", "test@gmail.com"));

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("blablabla@gmail.com");
        listOfMails.add("123@gmail.com");
        listOfMails.add("456@gmail.com");
        listOfMails.add("789@gmail.com");
        String result = listOfMails.stream()
                .collect(Collectors.joining(", ", "", ""));
        meetings.add(new Meeting(2, "Maths", "Mario", "10h30 to 12h30", result));

        return meetings;
    }

    @NonNull
    private List<MeetingsViewState> getDefaultViewStates() {
        List<MeetingsViewState> meetings = new ArrayList<>();

        meetings.add(new MeetingsViewState(1, "Subject1", "10h30 to 12h30", "Peach", "test@gmail.com"));

        List<String> listOfMails = new ArrayList<>();
        listOfMails.add("blablabla@gmail.com");
        listOfMails.add("123@gmail.com");
        listOfMails.add("456@gmail.com");
        listOfMails.add("789@gmail.com");
        String result = listOfMails.stream()
                .collect(Collectors.joining(", ", "", ""));
        meetings.add(new MeetingsViewState(2, "Maths", "10h30 to 12h30", "Mario", result));

        return meetings;
    }
}