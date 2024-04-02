package algonquin.cst2335.finalproject.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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

import algonquin.cst2335.finalproject.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SavedRecipeSummaryTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void savedRecipeSummary() {
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
                allOf(withId(R.id.fav_list), withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myToolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewFavRecipes),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
//        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction linearLayout = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.summaryTextView), withText("Bread Bowl Au Gratin requires roughly 45 minutes from start to finish. This recipe serves 2 and costs $2.18 per serving. One serving contains 586 calories, 38g of protein, and 34g of fat. If you have evoo, cream of mushroom soup, chicken stock, and a few other ingredients on hand, you can make it. 1 person were glad they tried this recipe. It works well as a side dish. It is brought to you by Foodista. All things considered, we decided this recipe deserves a spoonacular score of 71%. This score is solid. Try Best Au Gratin Potato Bread (Baked in Bread Machine), Bread Bowl Chicken Pot Pie (Coffin Bread), and Crisp Tomato, Zucchini and Eggplant Bread Gratin for similar recipes."),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.summaryTextView), withText("Bread Bowl Au Gratin requires roughly 45 minutes from start to finish. This recipe serves 2 and costs $2.18 per serving. One serving contains 586 calories, 38g of protein, and 34g of fat. If you have evoo, cream of mushroom soup, chicken stock, and a few other ingredients on hand, you can make it. 1 person were glad they tried this recipe. It works well as a side dish. It is brought to you by Foodista. All things considered, we decided this recipe deserves a spoonacular score of 71%. This score is solid. Try Best Au Gratin Potato Bread (Baked in Bread Machine), Bread Bowl Chicken Pot Pie (Coffin Bread), and Crisp Tomato, Zucchini and Eggplant Bread Gratin for similar recipes."),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));
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
