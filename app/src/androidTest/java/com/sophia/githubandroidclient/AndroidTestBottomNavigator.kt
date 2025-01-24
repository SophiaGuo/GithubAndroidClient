package com.sophia.githubandroidclient

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sophia.githubandroidclient.view.activity.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author SophiaGuo
 * Test BottomNavigator
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestBottomNavigator {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sophia.githubandroidclient", appContext.packageName)
    }

    @Test
    fun testMenuJump() {
        onView(withId(R.id.layout_fragment_home)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.me)).perform(click())
        onView(withId(R.id.layout_fragment_me)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.layout_fragment_search)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.layout_fragment_home)).check(matches(ViewMatchers.isDisplayed()))
    }
}
