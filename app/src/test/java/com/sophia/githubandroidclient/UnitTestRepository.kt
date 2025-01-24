package com.sophia.githubandroidclient

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sophia.githubandroidclient.repository.FollowerRepository
import com.sophia.githubandroidclient.repository.FollowingRepository
import com.sophia.githubandroidclient.repository.LoginRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author SophiaGuo
 */
@RunWith(AndroidJUnit4::class)
class UnitTestRepository {
    private var login: String? = null

    @Test
    fun testLoginRepository() {
        var loginRepository = LoginRepository()
        GlobalScope.launch {
            var userInfo = loginRepository.login(BuildConfig.USER_ACCESS_TOKEN)
            assertNotNull(userInfo)
            assertNotNull(userInfo.login)
            login = userInfo.login
        }
    }

    @Test
    fun testFollowerRepository() {
        if(login == null) {
            testLoginRepository()
        }
        assertNotNull(login)

        var followerRepository = FollowerRepository()
        GlobalScope.launch {
            var followerList = followerRepository.getFollowers(login!!)
            assertNotNull(followerList)
            assertFalse(followerList.isEmpty())
        }
    }

    @Test
    fun testFollowingRepository() {
        if(login == null) {
            testLoginRepository()
        }
        assertNotNull(login)

        var followingRepository = FollowingRepository()
        GlobalScope.launch {
            var followingList = followingRepository.getFollowing(login!!)
            assertNotNull(followingList)
            assertFalse(followingList.isEmpty())
        }
    }
}



