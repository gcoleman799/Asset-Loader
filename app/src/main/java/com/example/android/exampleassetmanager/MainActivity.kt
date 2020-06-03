//Copyright
//
//Copyright (C) 2020 The Android Open Source Project
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.example.android.exampleassetmanager

import android.media.Image
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import com.example.android.exampleassetmanager.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var assetLoader: WebViewAssetLoader? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var textWebView: WebView
    private lateinit var photoWebView: WebView
    private lateinit var internalWebView: WebView
    //from java doc


    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (view != null) {
                view.loadUrl(url)
            }
            return true

        }

        @RequiresApi(21)
        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest
        ): WebResourceResponse? {
            return assetLoader!!.shouldInterceptRequest(request.url)
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bind objects
        textWebView = binding.textWebview
        photoWebView = binding.photoWebview

        //set clients
        photoWebView.webViewClient = MyWebViewClient()
        textWebView.webViewClient = MyWebViewClient()

        photoWebView.settings.javaScriptEnabled = true
        photoWebView.settings.setSupportMultipleWindows(true)





        setTitle(R.string.app_name);
        //WebkitHelpers.appendWebViewVersionToTitle(this);

        //configure the asset loader with default domain and path for res and assets
        assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler(
                "/res/",
                WebViewAssetLoader.ResourcesPathHandler(this)
            )
            .addPathHandler("/assets/", AssetsPathHandler(this))

            .build();


        // set the path to the text to display
        val pathText = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            //.appendPath("res")
            //  .appendPath("www")
            .appendPath("myText.html")
            .build()

        //set the path to the photo to display
        val pathPhoto = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("res")
            .appendPath("drawable")
            //  .appendPath("www")
            .appendPath("dog.jpg")
            .build()


        photoWebView.loadUrl(pathPhoto.toString())
        textWebView.loadUrl(pathText.toString())

        


    }

    override fun onBackPressed() {
        if (photoWebView.canGoBack()) {
            photoWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    fun loadNew(){
        //set the path to the photo to display
        val pathBernese = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("res")
            .appendPath("drawable")
            //  .appendPath("www")
            .appendPath("bernese.jpg")
            .build()

        photoWebView.loadUrl(pathBernese.toString())
    }

    fun doubleText(){
        //set the path to the photo to display
        val pathText2 = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            //.appendPath("res")
            //  .appendPath("www")
            .appendPath("myText.html")
            .build()


        photoWebView.loadUrl(pathText2.toString())
    }



//    val button: Button = binding.switchButton
//    button.setOnClickListener {
//
//        photoWebView.loadUrl(path.toString())
//    }

}
//    override fun onDestroy() {
//        super.onDestroy()
//        // Clean the test/demo file for tests.
//        mDemoFile.delete()
//        mPublicDir.delete()
//    }
//
//    // Writes to file asynchronously in the background thread.
//    private class WriteFileTask internal constructor(
//        private val mFile: File,
//        private val mContent: String
//    ) :
//        AsyncTask<Void?, Void?, Void?>() {
//        protected override fun doInBackground(vararg params: Void): Void? {
//            mFile.parentFile.mkdirs()
//            try {
//                FileOutputStream(mFile).use({ fos -> fos.write(mContent.getBytes(UTF_8)) })
//            } catch (e: IOException) {
//                throw RuntimeException(e)
//            }
//            return null
//        }
//
//    }



