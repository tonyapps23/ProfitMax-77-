package com.codes.profmaxcopp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        getUrlIntent()
    }

    fun getUrlIntent(){
        val i = intent
        val myWebView: WebView = findViewById(R.id.webview)
        val url = i.getStringExtra("Url")
        myWebView.loadUrl("$url")

    }
}