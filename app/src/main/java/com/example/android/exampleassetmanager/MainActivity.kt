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
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure the asset loader with default domain and path for res and assets
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(this))
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()

        //set clients
        binding.textWebview.webViewClient = MyWebViewClient(assetLoader)

        setTitle(R.string.app_name)

        // set the path to the text to display
        val pathText = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            //.appendPath("res")
            //  .appendPath("www")
            .appendPath("myText.html")
            .build()

        binding.textWebview.loadUrl(pathText.toString())
    }

}