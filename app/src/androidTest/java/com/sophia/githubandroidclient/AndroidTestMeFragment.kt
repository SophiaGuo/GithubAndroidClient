package com.sophia.githubandroidclient


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sophia.githubandroidclient.view.activity.MainActivity
import com.sophia.githubandroidclient.view.fragment.MeFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @author SophiaGuo
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestMeFragment {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Test fragment is visible
     */
    @Test
    fun testFragmentMeInActivity() {
        onView(withId(R.id.fragment_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.layout_fragment_me)).check(matches(isDisplayed()))
    }

    @Test
    fun testJumpToReposActivity(){
        val scenario = launchFragmentInContainer<MeFragment>()
        scenario.onFragment {
            onView(withId(R.id.layout_my_repos)).perform(click())
            onView(withId(R.id.activity_repos)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testJumpToFollowersActivity(){
        val scenario = launchFragmentInContainer<MeFragment>()
        scenario.onFragment {
            onView(withId(R.id.layout_followers)).perform(click())
            onView(withId(R.id.activity_follower)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testJumpToFolloweringActivity(){
        val scenario = launchFragmentInContainer<MeFragment>()
        scenario.onFragment {
            onView(withId(R.id.layout_following)).perform(click())
            onView(withId(R.id.activity_following)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testJumpToSettingActivity(){
        val scenario = launchFragmentInContainer<MeFragment>()
        scenario.onFragment {
            onView(withId(R.id.layout_settings)).perform(click())
            onView(withId(R.id.activity_settings)).check(matches(isDisplayed()))
        }
    }
}
