package org.ciaranroche.kang

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertNotNull
import org.ciaranroche.kang.activities.StartUpActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LogoutTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)
    private val startUpMonitor = getInstrumentation().addMonitor(StartUpActivity::class.java!!.name, null, false)
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
    fun logout_success() {
        Log.e("@Test", "Performing Logout success test")
        login()

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        kang_wait()

        Espresso.onView(ViewMatchers.withText(R.string.logout)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(R.id.logoutBtn)).perform(ViewActions.click())
        kang_wait()

        val startUpActivity = getInstrumentation().waitForMonitorWithTimeout(startUpMonitor, 5000)
        assertNotNull(startUpActivity)
    }
}