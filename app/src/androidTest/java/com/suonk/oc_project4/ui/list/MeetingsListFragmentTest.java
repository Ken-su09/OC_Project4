package com.suonk.oc_project4.ui.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.suonk.oc_project4.ui.utils.CreateMeetingUtils.createMeeting;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.ui.activities.MainActivity;
import com.suonk.oc_project4.ui.create.CreateMeetingFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingsListFragmentTest {

    private MainActivity activityRef;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
    }

    @After
    public void tearDown() {
        activityRef = null;
    }

    @Test
    public void meetings_list_should_be_empty() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void meetings_list_should_not_be_empty_after_creation() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetings_list_should_be_empty_after_creation_and_delete() {
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));

        onView(allOf(withId(R.id.delete_button), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void after_creation_two_meetings__meetings_list_size_should_be_two() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));

        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 2, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(2)));
    }

    @Test
    public void after_creation_two_meetings_same_place_same_hour_should_display_create_fragment_root() {
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));

        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting_root)).check(matches(isDisplayed()));
    }

    @Test
    public void meetings_list_creation_two_meetings_not_same_places_then_filter_by_place_peach_should_show_only_one() {
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject2", 0, "12h30", "14h30", "test2@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(2)));

        onView(withId(R.id.place_peach)).perform();

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void creation_two_meetings_not_same_places_then_filter_by_date_meetings_list_should_be_empty() {
        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject1", 1, "10h30", "12h30", "test@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.add_meeting), isDisplayed())).perform(click());

        createMeeting("Subject2", 0, "12h30", "14h30", "test2@hotmail.com");
        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(2)));

        onView(withId(R.id.date_over_8_under_10)).perform();

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(0)));
    }
}