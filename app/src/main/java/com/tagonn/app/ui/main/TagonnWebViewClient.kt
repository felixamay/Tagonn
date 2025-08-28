package com.tagonn.app.ui.main

import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tagonn.app.R

class TagonnWebViewClient(private val activity: MainActivity) : WebViewClient() {
    
    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
        super.onPageStarted(view, url, favicon)
        activity.onPageStarted()
    }
    
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        activity.onPageFinished()
    }
    
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return false
        
        return when {
            // Keep tagonn.com links inside the app
            url.contains("tagonn.com") -> {
                view?.loadUrl(url)
                true
            }
            // Handle external links (mailto, tel, etc.)
            url.startsWith("mailto:") || url.startsWith("tel:") || url.startsWith("sms:") -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                    true
                } catch (e: Exception) {
                    false
                }
            }
            // Handle other external links
            !url.startsWith("http://") && !url.startsWith("https://") -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intent)
                    true
                } catch (e: Exception) {
                    false
                }
            }
            // Open external links in browser
            else -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                activity.startActivity(intent)
                true
            }
        }
    }
    
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        activity.onReceivedError()
    }
    
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: android.webkit.WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (request?.isForMainFrame == true) {
            activity.onReceivedError()
        }
    }
}