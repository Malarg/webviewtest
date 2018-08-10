/*
 * Copyright (C) 2018 Gravity group
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gravitygroup.taingle.zenmoneyexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.*
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    companion object {
        const val JAVASCRIPT_OBJ = "test"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val code = loadResource(R.raw.webview)

        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.addJavascriptInterface(JavaScriptInterface(textView), JAVASCRIPT_OBJ)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.evaluateJavascript("retft();") {print(it.toString())}
            }

//            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
//                if (request?.method.equals("OPTIONS")) {
//                    return OptionsAllowResponse.build()
//                }
//
//                return null
//            }
        }
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("file:///android_asset/webview.html")
    }

    private fun loadResource(resId: Int): String {
        val builder = StringBuilder()
        val codeStream = applicationContext.resources.openRawResource(resId)
        val codeReader = BufferedReader(InputStreamReader(codeStream))
        builder.ensureCapacity(codeStream.available())
        codeReader.readLines().forEach { builder.append(it) }
        return builder.toString()
    }
}

class JavaScriptInterface(val textView: TextView) {
    @JavascriptInterface
    fun mult(a: String) {
        textView.text = a
    }
}