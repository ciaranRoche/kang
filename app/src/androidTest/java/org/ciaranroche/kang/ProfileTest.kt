package org.ciaranroche.kang

import android.util.Log
import android.widget.DatePicker
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.ciaranroche.kang.activities.StartUpActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import com.google.firebase.auth.FirebaseAuth
import junit.framework.Assert
import org.ciaranroche.kang.activities.MainActivity
import org.ciaranroche.kang.helpers.kang_wait
import org.hamcrest.Matchers

@RunWith(AndroidJUnit4::class)
class ProfileTest {

    private val testActivityRule = ActivityTestRule(StartUpActivity::class.java, true, true)
    private val mainActivityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(MainActivity::class.java!!.name, null, false)

    @Rule
    fun rule() = testActivityRule

    private val username_typed = "rick@morty.com"
    private val correct_password = "123456"
    private val username = "Rick Sanchez"
    private val ogbio = "From Earth dimension C-137, wubba lubba dub dub!!!"
    private val update_bio = " Wubba"
    private val new_user = "test@boop.com"

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

    @Test
    fun update_profile_success() {
        Log.e("@Test", "Performing update profile success test")
        login()

        Espresso.onView(withId(R.id.userProfileFragment)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userBio)).check(matches(withText(ogbio)))

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        kang_wait()

        Espresso.onView(withText(R.string.settings)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(R.id.set_bio)).perform(clearText(), ViewActions.typeText(update_bio))
        kang_wait()

        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.accept_button)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userProfileFragment)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userBio)).check(matches(withText(update_bio)))

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        kang_wait()

        Espresso.onView(withText(R.string.settings)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(R.id.set_bio)).perform(clearText(), ViewActions.typeText(ogbio))
        kang_wait()

        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.accept_button)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userProfileFragment)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.userBio)).check(matches(withText(ogbio)))
    }

    @Test
    fun delete_user_test() {
        Log.e("@Test", "Performing sign up success test")
        kang_wait()
        Espresso.onView(withId(R.id.signUpBtn)).perform(ViewActions.click())

        Espresso.onView((withId(R.id.signupEmail))).perform(ViewActions.typeText(new_user))

        Espresso.onView((withId(R.id.signupPassword))).perform(ViewActions.typeText(correct_password))

        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.signUpBtn)).perform(ViewActions.click())
        kang_wait()
        kang_wait()
        Espresso.onView((withId(R.id.username))).perform(ViewActions.typeText(new_user))

        Espresso.onView((withId(R.id.userbio))).perform(ViewActions.typeText(new_user))

        Espresso.closeSoftKeyboard()

        Espresso.onView((withId(R.id.dobBtn))).perform(ViewActions.click())

        Espresso.onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(PickerActions.setDate(1960, 6, 19))

        Espresso.onView(withId(android.R.id.button1)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.acceptBtn)).perform(ViewActions.click())
        kang_wait()

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        kang_wait()

        Espresso.onView(ViewMatchers.withText(R.string.logout)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(R.id.logoutBtn)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView((ViewMatchers.withId(R.id.login_username)))
            .perform(ViewActions.typeText(new_user))

        Espresso.onView(ViewMatchers.withId(R.id.login_password))
            .perform(ViewActions.typeText(correct_password))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.loginBtn))
            .perform(ViewActions.click())
        kang_wait()
        kang_wait()

        Espresso.onView(withId(R.id.userProfileFragment)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(R.id.deleteUserButton)).perform(ViewActions.click())
        kang_wait()

        Espresso.onView(withId(android.R.id.button1)).perform(ViewActions.click())
        kang_wait()
        kang_wait()

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(new_user, correct_password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Assert.assertTrue(false)
            } else {
                Assert.assertTrue(true)
            }
        }
    }
}