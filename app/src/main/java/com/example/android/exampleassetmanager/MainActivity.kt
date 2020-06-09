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

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewFeature
import com.example.android.exampleassetmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private class MyWebViewClient(private val assetLoader: WebViewAssetLoader) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest
        ) = assetLoader.shouldInterceptRequest(request.url)
    }

    /** Instantiate the interface and set the context  */
    class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun changePage() {

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure the asset loader with default domain and path for res and assets
        val assetLoader = WebViewAssetLoader.Builder()
            //.setDomain("https://www.iana.org")
            .addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(this))
            .addPathHandler("/assets/", AssetsPathHandler(this))

            //.addPathHandler("/domains/example/", AssetsPathHandler(this))
            .build()

        //set clients
        binding.webview.webViewClient = MyWebViewClient(assetLoader)

        setTitle(R.string.app_name)


        //enable java script
        binding.webview.settings.javaScriptEnabled = true

        //set dark mode
        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(binding.webview.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }

        //Bind that webAppInterface class to the JS and name it Android
        binding.webview.addJavascriptInterface(WebAppInterface(this),  "Dogs")

        // set the path to the text to display
        val path = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            .appendPath("myText.html")
            .build()

        binding.webview.loadUrl(path.toString())
       // binding.webview.loadUrl("https://example.com/")
    }


    //allow users to navigate back to a previous page
    override fun onBackPressed() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            super.onBackPressed()
        }



    }



}