package org.ciaranroche.kang

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.ciaranroche.kang.activities.StartUpActivity
import org.ciaranroche.kang.helpers.kang_wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VinylTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)

    @Rule
    fun rule() = testActivityRule

    private val username_typed = "rick@morty.com"
    private val correct_password = "123456"
    private val test_artist = "A Test"
    private val test_title = "A Test Title"
    private val test_about = "A Test About"

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
    fun add_vinyl_success() {
        login()

        Espresso.onView(ViewMatchers.withId(R.id.add_vinyl)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.add_vinyl)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView((ViewMatchers.withId(R.id.artist_name_input)))
            .perform(ViewActions.typeText(test_artist))

        Espresso.onView((ViewMatchers.withId(R.id.vinyl_name_input)))
            .perform(ViewActions.typeText(test_title))

        Espresso.onView((ViewMatchers.withId(R.id.vinyl_desc_input)))
            .perform(ViewActions.typeText(test_about))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.next_button))
            .perform(ViewActions.click())
        kang_wait()

        Espresso.onView(ViewMatchers.withId(R.id.move_button))
            .perform(ViewActions.click())
        kang_wait()

        Espresso.onView(ViewMatchers.withId(R.id.welcomeFragment)).perform(ViewActions.click())
        kang_wait()
        Espresso.onView(ViewMatchers.withId(R.id.welcomeFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}