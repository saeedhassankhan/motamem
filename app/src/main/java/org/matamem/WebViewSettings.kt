package org.matamem

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView


class WebViewSettings {
    fun setSettings(webView: WebView) {

        webView.settings.domStorageEnabled = true;
        webView.settings.loadsImagesAutomatically = true;
        webView.settings.javaScriptEnabled = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }
}