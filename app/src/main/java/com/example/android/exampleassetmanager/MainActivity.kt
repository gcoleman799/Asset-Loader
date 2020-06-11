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
import android.system.Os.open
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewFeature
import com.example.android.exampleassetmanager.databinding.ActivityMainBinding
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.AsynchronousServerSocketChannel.open
import java.nio.channels.DatagramChannel.open


class MainActivity : AppCompatActivity() {
    private class MyWebViewClient(private val assetLoader: WebViewAssetLoader) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }



            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                
                return if (request.url.lastPathSegment == "Asset-Loader") {
                    
                    //Take the requested location and add index.html, this means that the asset loader is
                    //being used to redirect url call to this specific website and using local assets for this
                    //location only but that all other https requests will be handeled with network calls
                    var newStringDest = request.url.toString()+"index.html"
                    var newDest= Uri.parse(newStringDest)
                    assetLoader.shouldInterceptRequest(newDest)
                } else {
                    return null
                }
            }


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
        //with the asset loader we are just resetting the
        val assetLoader = WebViewAssetLoader.Builder()
            .setDomain("gcoleman799.github.io")
            .addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(this))
            //.addPathHandler("/assets/", AssetsPathHandler(this))
            .addPathHandler("/Asset-Loader/", AssetsPathHandler(this))
            .build()

        //set clients
        binding.textWebview.webViewClient = MyWebViewClient(assetLoader)

        setTitle(R.string.app_name)


        //enable java script
        binding.textWebview.settings.javaScriptEnabled = true

        //set dark mode
        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(binding.textWebview.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }

        //Bind that webAppInterface class to the JS and name it Android
        binding.textWebview.addJavascriptInterface(WebAppInterface(this),  "Dogs")


        //here we are directing to the index file in assets. We can access this local file because the asset loader above
        //redefines the path we use to access our assets folder
        binding.textWebview.loadUrl("https://gcoleman799.github.io/Asset-Loader/")
    }


    //allow users to navigate back to a previous page
    override fun onBackPressed() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (binding.textWebview.canGoBack()) {
            binding.textWebview.goBack()
        } else {
            super.onBackPressed()
        }



    }



}
