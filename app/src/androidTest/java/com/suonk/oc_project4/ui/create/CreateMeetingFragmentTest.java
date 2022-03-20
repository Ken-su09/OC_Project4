package com.suonk.oc_project4.ui.create;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.suonk.oc_project4.ui.utils.CreateMeetingUtils.createMeeting;
import static org.hamcrest.Matchers.allOf;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.ui.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateMeetingFragmentTest {

    private MainActivity activityRef;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);

        onView(allOf(withId(R.id.add_neighbour), isDisplayed())).perform(click());
    }

    @After
    public void tearDown() {
        activityRef = null;
    }

    @Test
    public void display_create_new_meeting_root() {
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }

    @Test
    public void create_new_meeting() {
        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(withId(R.id.meetings_rv)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void try_to_create_meeting_with_empty_subject() {
        createMeeting("", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }

    @Test
    public void try_to_create_meeting_with_empty_start_time() {
        createMeeting("", 1, "", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }

    @Test
    public void try_to_create_meeting_with_empty_end_time() {
        createMeeting("", 1, "10h30", "", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }

    @Test
    public void try_to_create_meeting_with_empty_emails() {
        createMeeting("", 1, "10h30", "12h30", "");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }
}