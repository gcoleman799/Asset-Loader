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

import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import com.example.android.exampleassetmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var assetLoader: WebViewAssetLoader? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    inner class MyWebViewClient : WebViewClient() {

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

        webView = binding.activityMainWebview

        //webView.webViewClient = WebViewClient()
      //  webView.settings.javaScriptEnabled = true

        webView.webViewClient = MyWebViewClient()


        assetLoader = WebViewAssetLoader.Builder()
            .setDomain("www.iana.org")
            .addPathHandler("/assets/", AssetsPathHandler(this))
            // .addPathHandler("/index.html/", ResourcesPathHandler(this))
            .build()


        val path = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            //.appendPath("www")
            .appendPath("index.html")
            .build()

        //webView.loadUrl(path.toString());
        webView.loadUrl("https://www.example.com")



    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}

