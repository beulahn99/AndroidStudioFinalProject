package algonquin.cst2335.finalproject.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
public class RecipeDetailsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void recipeDetailTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.item_recipes), withContentDescription("Recipe Search App"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.recipeEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Cream"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
//        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView), withContentDescription("Recipe image"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.summaryTextView), withText("You can never have too many dessert recipes, so give Cream Cheese Cookies a try. This recipe serves 40 and costs 6 cents per serving. One serving contains 54 calories, 0g of protein, and 1g of fat. 1 person were glad they tried this recipe. If you have egg white, icing sugar, baking powder, and a few other ingredients on hand, you can make it. It is brought to you by Foodista. From preparation to the plate, this recipe takes around 45 minutes. Taking all factors into account, this recipe earns a spoonacular score of 7%, which is improvable. If you like this recipe, take a look at these similar recipes: Sour Cream Cut-Out Cookies With Cream Cheese Icing, Chocolate Cookies and Cream Cupcakes with Cream Cheese Frosting, and Cream Cheese M and M�s Cookies."),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("You can never have too many dessert recipes, so give Cream Cheese Cookies a try. This recipe serves 40 and costs 6 cents per serving. One serving contains 54 calories, 0g of protein, and 1g of fat. 1 person were glad they tried this recipe. If you have egg white, icing sugar, baking powder, and a few other ingredients on hand, you can make it. It is brought to you by Foodista. From preparation to the plate, this recipe takes around 45 minutes. Taking all factors into account, this recipe earns a spoonacular score of 7%, which is improvable. If you like this recipe, take a look at these similar recipes: Sour Cream Cut-Out Cookies With Cream Cheese Icing, Chocolate Cookies and Cream Cupcakes with Cream Cheese Frosting, and Cream Cheese M and M�s Cookies.")));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.favoriteButton), withContentDescription("Save recipe button"),
                        withParent(allOf(withId(R.id.buttonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.sourceUrlButton), withContentDescription("Link button"),
                        withParent(allOf(withId(R.id.buttonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withText("Cream Cheese Cookies"),
                        withParent(allOf(withId(R.id.myToolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Cream Cheese Cookies")));
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
