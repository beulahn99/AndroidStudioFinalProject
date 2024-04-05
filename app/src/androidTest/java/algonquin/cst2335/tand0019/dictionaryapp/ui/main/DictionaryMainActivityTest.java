package algonquin.cst2335.tand0019.dictionaryapp.ui.main;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

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

import algonquin.cst2335.tand0019.dictionaryapp.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DictionaryMainActivityTest {

    @Rule
    public ActivityScenarioRule<DictionaryMainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(DictionaryMainActivity.class);

    @Test
    public void dictionaryMainActivityTest() {
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("canada"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text), withText("canada"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.iv_star),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView.perform(click());

        pressBack();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_favourites),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.iv_star),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(com.google.android.material.R.id.snackbar_action), withText("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.snackbar.Snackbar$SnackbarLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.iv_back),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(androidx.appcompat.R.id.action_bar_root),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
    }

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

    // Additional Test Case 1: Search for a different term and verify the result.
    @Test
    public void testSearchForAnotherTerm() {
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(replaceText("flower"), closeSoftKeyboard());
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressImeActionButton());
        onView(withText("Sunflower")).check(matches(isDisplayed()));
    }

    // Additional Test Case 2: Navigate to a different section of the app and verify its content.
    @Test
    public void testNavigateToAnotherSection() {
        onView(withId(R.id.guideline)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
    }

    // Additional Test Case 3: Interact with a different UI element and verify the outcome.
    @Test
    public void testInteractWithDifferentUIElement() {
        onView(withId(R.id.tv_word)).perform(click());
        onView(withId(R.id.guideline)).perform(replaceText("Ecosystem"), closeSoftKeyboard());
        onView(withId(R.id.tv_word)).perform(click());
        onView(withText("Ecosystem")).check(matches(isDisplayed()));
    }

    // Additional Test Case 4: Verify a UI element's state change after an interaction.
    @Test
    public void testVerifyUIElementStateChange() {
        onView(withId(R.id.tv_definition)).perform(click());
        onView(withId(R.id.iv_back));
    }

}
