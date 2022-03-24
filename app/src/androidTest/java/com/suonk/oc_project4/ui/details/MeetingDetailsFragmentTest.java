package com.suonk.oc_project4.ui.details;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.suonk.oc_project4.ui.utils.CreateMeetingUtils.createMeeting;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.ui.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeetingDetailsFragmentTest {

    private MainActivity activityRef;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());
        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
    }

    @After
    public void tearDown() {
        activityRef = null;
    }

    @Test
    public void should_display_meeting_details_root() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.meeting_details_root)).check(matches(isDisplayed()));
    }

    @Test
    public void meeting_data_created_equals_to_meeting_details() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.subject_input_edit_text)).check(matches(withText("Subject1")));
        onView(withId(R.id.spinner_place)).check(matches(withSpinnerText(containsString("Mario"))));
        onView(withId(R.id.start_time_edit_text)).check(matches(withText("10h30")));
        onView(withId(R.id.end_time_edit_text)).check(matches(withText("12h30")));
    }
}