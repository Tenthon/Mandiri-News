package com.mandiri.news.view.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mandiri.news.R
import com.mandiri.news.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar_transparant.*


class DetailActivity : BaseActivity() {
    var url = ""
    var desc = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setToolbar()
        url = intent.getStringExtra("url")
        desc = intent.getStringExtra("desc")

        webView.loadUrl(url)
        webView.webChromeClient = WebChromeClient()
        webView.settings.domStorageEnabled = false
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar2.visibility = View.GONE
                view?.loadUrl(url)
            }
        }

        btnShare.setOnClickListener(View.OnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = desc
            val shareBody: String = url
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, desc)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        })
    }
}