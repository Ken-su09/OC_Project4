package com.suonk.oc_project4.ui.utils;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.suonk.oc_project4.R;

public class CreateMeetingUtils {

    public static void createMeeting(@Nullable final String subject,
                                     @Nullable final Integer position,
                                     @Nullable final String startTime,
                                     @Nullable final String endTime,
                                     @Nullable final String listOfMails) {
        if (subject != null) {
            setMeetingSubject(subject);
        }

        if (position != null) {
            setMeetingPlace(position);
        }

        if (startTime != null) {
            setMeetingStartTime(startTime);
        }

        if (endTime != null) {
            setMeetingEndTime(endTime);
        }

        if (listOfMails != null) {
            setMeetingListOfMails(listOfMails);
        }
    }

    public static void setMeetingSubject(@NonNull String subject) {
        onView(withId(R.id.subject_input_edit_text)).perform(
                replaceText(subject),
                closeSoftKeyboard());
    }

    public static void setMeetingPlace(@NonNull Integer position) {
        onView(withId(R.id.spinner_place)).perform(click());
        onData(anything()).atPosition(position).perform(click());

//        onView(withId(R.id.spinner_place)).perform(
//                replaceText(place),
//                closeSoftKeyboard());
    }

    public static void setMeetingStartTime(@NonNull String startTime) {
        onView(withId(R.id.start_time_edit_text)).perform(
                replaceText(startTime),
                closeSoftKeyboard());
    }

    public static void setMeetingEndTime(@NonNull String endTime) {
        onView(withId(R.id.end_time_edit_text)).perform(
                replaceText(endTime),
                closeSoftKeyboard());
    }

    public static void setMeetingListOfMails(@NonNull String listOfMails) {
        onView(withId(R.id.list_emails_input_edit_text)).perform(
                replaceText(listOfMails),
                pressImeActionButton());
    }
}
