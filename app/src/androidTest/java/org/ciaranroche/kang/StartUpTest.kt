package org.ciaranroche.kang

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.ciaranroche.kang.activities.StartUpActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.ciaranroche.kang.activities.MainActivity
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class StartUpTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)
    private val mainActivityMonitor = getInstrumentation().addMonitor(MainActivity::class.java!!.getName(), null, false)

    @Rule
    fun rule() = testActivityRule

    private val username_typed = "rick@morty.com"
    private val correct_password = "123456"
    private val wrong_password = "654321"
    private val random_name = getRandomString(10)
    private val new_user = "$random_name@boop.com"

    @Before
    fun setUp() {
        rule()
    }

    @Test
    fun login_success() {
        Log.e("@Test", "Performing login success test")
        wait_splash()
        Espresso.onView((withId(R.id.login_username)))
            .perform(ViewActions.typeText(username_typed))

        Espresso.onView(withId(R.id.login_password))
            .perform(ViewActions.typeText(correct_password))

        Espresso.onView(withId(R.id.loginBtn))
            .perform(ViewActions.click())

        val mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000)
        assertNotNull(mainActivity)
    }

    @Test
    fun login_failure() {
        Log.e("@Test", "Performing login failure test")
        wait_splash()
        Espresso.onView((withId(R.id.login_username)))
            .perform(ViewActions.typeText(username_typed))

        Espresso.onView(withId(R.id.login_password))
            .perform(ViewActions.typeText(wrong_password))

        Espresso.onView(withId(R.id.loginBtn))
            .perform(ViewActions.click())

        val mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000)
        assertNull(mainActivity)
    }

    @Test
    fun sign_up_success() {
        Log.e("@Test", "Performing sign up success test")
        wait_splash()
        Espresso.onView(withId(R.id.signUpBtn)).perform(ViewActions.click())

        Espresso.onView((withId(R.id.signupEmail))).perform(ViewActions.typeText(new_user))

        Espresso.onView((withId(R.id.signupPassword))).perform(ViewActions.typeText(correct_password))

        Espresso.onView(withId(R.id.signUpBtn)).perform(ViewActions.click())
        wait_splash()
        Espresso.onView((withId(R.id.username))).perform(ViewActions.typeText(new_user))

        Espresso.onView((withId(R.id.userbio))).perform(ViewActions.typeText(new_user))

        Espresso.onView((withId(R.id.userbio))).perform(closeSoftKeyboard())

        Espresso.onView(withId(R.id.acceptBtn)).perform(ViewActions.click())

        val mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000)
        assertNotNull(mainActivity)
    }

    private fun wait_splash() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun getRandomString(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}