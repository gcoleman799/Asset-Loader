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
import java.io.File


class MainActivity : AppCompatActivity() {
    var assetLoader: WebViewAssetLoader? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var textWebView: WebView
    private lateinit var photoWebView: WebView
   

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

        //bind objects
        textWebView = binding.textWebview
        photoWebView = binding.photoWebview

        //set clients
        photoWebView.webViewClient = MyWebViewClient()
        textWebView.webViewClient = MyWebViewClient()



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
            //.appendPath("resources")
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



        textWebView.loadUrl(pathText.toString())
        photoWebView.loadUrl(pathPhoto.toString())


    }

    override fun onBackPressed() {
        if (photoWebView.canGoBack()) {
            photoWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}

