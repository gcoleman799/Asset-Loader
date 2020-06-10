package com.example.android.exampleassetmanager


import android.R
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.DriverAtoms.webClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
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

//     @Rule
//     var mRule: IntegrationActivityTestRule<MainActivity> = IntegrationActivityTestRule(
//        MainActivity::class.java,
//         R.id.webview
//     )

//    @Test
//    open fun testAssetLoaderSimpleActivity() {
//        mRule.assertHtmlElementContainsText(
//            R.id.webview, "assets_success_msg",
//            "Successfully loaded html from assets!"
//        )
//    }

}

















