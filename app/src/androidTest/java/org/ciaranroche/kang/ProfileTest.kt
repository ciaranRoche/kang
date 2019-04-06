package org.ciaranroche.kang

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.ciaranroche.kang.activities.StartUpActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)

    @Rule
    fun rule() = testActivityRule

    private val username_typed = "rick@morty.com"
    private val correct_password = "123456"
    private val username = "Rick Sanchez"

    @Before
    fun setUp() {
        rule()
    }

    fun login() {
        Log.e("@Test", "Logging In")
        kang_wait()
        Espresso.onView((ViewMatchers.withId(R.id.login_username)))
            .perform(ViewActions.typeText(username_typed))

        Espresso.onView(ViewMatchers.withId(R.id.login_password))
            .perform(ViewActions.typeText(correct_password))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.loginBtn))
            .perform(ViewActions.click())

        kang_wait()
        kang_wait()
    }

    @Test
    fun profile_success() {
        Log.e("@Test", "Performing profile success test")
        login()

        Espresso.onView(withId(R.id.userProfileFragment)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userName)).check(matches(withText(username)))
    }
}