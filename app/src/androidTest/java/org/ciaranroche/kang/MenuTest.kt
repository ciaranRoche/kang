package org.ciaranroche.kang

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.ciaranroche.kang.activities.StartUpActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MenuTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)

    @Rule
    fun rule() = testActivityRule

    private val username_typed = "rick@morty.com"
    private val correct_password = "123456"

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
    fun menu_success() {
        Log.e("@Test", "Performing menu success test")
        login()

        Espresso.onView(ViewMatchers.withId(R.id.userProfileFragment)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.userProfileFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.chatFragment)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.chatFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.add_vinyl)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.add_vinyl)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.welcomeFragment)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.welcomeFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}