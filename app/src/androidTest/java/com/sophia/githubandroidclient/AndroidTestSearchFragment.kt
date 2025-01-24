package com.sophia.githubandroidclient

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sophia.githubandroidclient.view.activity.MainActivity
import com.sophia.githubandroidclient.view.fragment.SearchFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author SophiaGuo
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestSearchFragment {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Test fragment is visible
     */
    @Test
    fun testSearchFragmentInActivity() {
        onView(withId(R.id.fragment_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.layout_fragment_search)).check(matches(isDisplayed()))
    }

    @Test
    fun testSwitchSearchType() {
        val scenario = launchFragmentInContainer<SearchFragment>()

        scenario.onFragment {
            onView(withId(R.id.button_search_type)).perform(click())
            onView(withId(R.id.text_search_hint)).check(matches(withText("输入关键词搜索用户")))
        }
    }
}
