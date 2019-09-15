package com.example.my_news;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MyNewsUITests {

    @Test
    public void checkRecyclerViewMatchesTitle() {
        RecyclerViewMatcher recyclerViewMatcher = new RecyclerViewMatcher(R.id.frag_recycler_view);
        recyclerViewMatcher.atPositionOnView(0, R.id.item_title).matches(isDisplayed());
        recyclerViewMatcher.atPositionOnView(0, R.id.item_summary).matches(isDisplayed());
        recyclerViewMatcher.atPositionOnView(0, R.id.item_date).matches(isDisplayed());
        recyclerViewMatcher.atPositionOnView(0, R.id.item_layout).matches(isDisplayed());
    }

    @Test
    public void isRecyclerViewVisible() {
        onView(withId(R.id.frag_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void isToolbarDisplayed() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

    }

    @Test
    public void tapSearchIcon_openSearchActivity() {
        onView((withId(R.id.menu_activity_main_search))).perform(click());
        onView(withId(R.id.activity_search_article)).check(matches(isDisplayed()));
    }
}
