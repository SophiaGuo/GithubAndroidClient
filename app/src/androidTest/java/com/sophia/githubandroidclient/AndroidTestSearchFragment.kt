package com.sophia.githubandroidclient

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sophia.githubandroidclient.view.activity.LoginActivity
import com.sophia.githubandroidclient.view.activity.MainActivity
import com.sophia.githubandroidclient.view.fragment.SearchFragment
import org.junit.Before
import org.junit.Rule
import java.text.DecimalFormat

/**
 * @author SophiaGuo
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestSearchFragment {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.sophia.githubandroidclient", appContext.packageName)
//    }


//    @Before
//    fun setup() {
//        launchFragmentInContainer<SearchFragment>()
//    }

//    @Rule
//    @JvmField
//    val activityRule = ActivityScenarioRule(LoginActivity::class.java)


//    @Test
//    fun testSum() {
//        onView(withId(R.id.button_login)).perform(click())
//
//
//        onView(withId(R.id.text_nickname)).check(matches(ViewMatchers.isDisplayed()))
//
//
////        onView(withId(R.id.button_search_type)).perform(click())
////        onView(withId(R.id.button_2)).perform(click())
////        onView(withId(R.id.button_3)).perform(click())
////        onView(withId(R.id.button_sum)).perform(click())
////        onView(withId(R.id.button_3)).perform(click())
////        onView(withId(R.id.button_2)).perform(click())
////        onView(withId(R.id.button_1)).perform(click())
////        onView(withId(R.id.button_calc)).perform(click())
////        onView(withId(R.id.text_nickname)).check(matches(withText("${123 + 321}")))
//    }



    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun testMenu() {
        onView(withId(R.id.layout_fragment_home)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.me)).perform(click())
        onView(withId(R.id.layout_fragment_me)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.layout_fragment_search)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.layout_fragment_home)).check(matches(ViewMatchers.isDisplayed()))
    }
}
