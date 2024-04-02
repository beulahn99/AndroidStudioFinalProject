package algonquin.cst2335.finalproject.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelpButtonTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void hepIconTest() {
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
                allOf(withId(R.id.instructions), withContentDescription("About"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myToolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message), withText("Home: Tap the home icon to return to the main menu.\n\nFavorite Recipes: Access your saved recipes by tapping the favorites icon.\n\nSearch: Look up recipes by typing in the search bar.\n\nSave Recipe: Tap the save icon to add a recipe to your favorites. Remove it by tapping the trash icon.\n\nRecipe Link: Click on the link icon to view the full recipe online.\n\nRecipe Details: Tap a recipe to view its details, including ingredients and instructions.\n\n\n\nVersion 1.0, created by Beulah Nwokotubo\n\n"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(matches(withText("Home: Tap the home icon to return to the main menu.  Favorite Recipes: Access your saved recipes by tapping the favorites icon.  Search: Look up recipes by typing in the search bar.  Save Recipe: Tap the save icon to add a recipe to your favorites. Remove it by tapping the trash icon.  Recipe Link: Click on the link icon to view the full recipe online.  Recipe Details: Tap a recipe to view its details, including ingredients and instructions.    Version 1.0, created by Beulah Nwokotubo  ")));
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
}
