package algonquin.cst2335.finalproject.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.finalproject.R;

/**
 * Purpose: This class contains Espresso UI tests for the Home button functionality in the RecipeSearchActivity.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 31st March, 2024
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeButtonTest {

    /**
     * Rule to launch the MainActivity before each test.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * UI test for the Home button functionality.
     */
    @Test
    public void homeButtonTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.item_recipes), withContentDescription("Recipe Search App"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.home_page), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myToolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.appIconImageView), withContentDescription("Main app icon"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.welcomeMessageTextView), withText("Welcome to The Multi-Utility App!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Welcome to The Multi-Utility App!")));

        ViewInteraction toolbar = onView(
                allOf(withId(R.id.main_toolbar),
                        isDescendantOfA(withId(android.R.id.content)),
                        isDisplayed()));
        toolbar.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withText("Final Project"),
                        withParent(allOf(withId(R.id.main_toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Final Project")));

        ViewInteraction imageView3 = onView(
                allOf(withContentDescription("More options"),
                        withParent(withParent(withId(R.id.main_toolbar))),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));
    }

    /**
     * Custom matcher to locate a child view at a specified position within a parent view.
     *
     * @param parentMatcher The matcher for the parent view.
     * @param position      The position of the child view within the parent view.
     * @return A matcher for the child view at the specified position.
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
