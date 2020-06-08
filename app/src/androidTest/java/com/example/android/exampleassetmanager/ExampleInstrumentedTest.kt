package com.example.android.exampleassetmanager


import android.app.PendingIntent.getActivity
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.DriverAtoms.webClick
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.android.material.internal.ContextUtils.getActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private const val MACCHIATO = "Macchiato"
private const val DOPPIO = "Doppio"

@LargeTest
@RunWith(AndroidJUnit4::class)
open class WebViewActivityTest {


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


//        @get:Rule val activityScenarioRule =
//            activityScenarioRule<MainActivity>()

    @Test
    fun testWebViewInteraction() {
        onWebView().forceJavascriptEnabled()
    }


    @Test
    fun checkLinkDestination() {
        onWebView()
            .withElement(findElement(androidx.test.espresso.web.webdriver.Locator.ID, "button")) // similar to onView(withId(...))
            .perform(webClick()) // Similar to perform(click())

            // Similar to check(matches(...))
            .check(webMatches(getCurrentUrl(), containsString("https://www.w3docs.com/")))
    }
}








