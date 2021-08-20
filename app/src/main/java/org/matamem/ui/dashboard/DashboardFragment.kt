package org.matamem.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import org.matamem.R
import org.matamem.WebViewSettings
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    @Inject lateinit var webViewSettings: WebViewSettings

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val webView: WebView = root.findViewById(R.id.webView)

        webViewSettings.setSettings(webView )

        dashboardViewModel.link.observe(viewLifecycleOwner, Observer {
            webView.loadUrl(it)
        })

        return root
    }
}