package algonquin.cst2335.finalproject.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.finalproject.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.item_recipes), withContentDescription("Recipe Search App"),
                        withParent(withParent(withId(R.id.main_toolbar))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withContentDescription("More options"),
                        withParent(withParent(withId(R.id.main_toolbar))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
    }
}
