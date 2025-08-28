package com.tagonn.app.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tagonn.app.R
import com.tagonn.app.databinding.ActivityMainBinding
import com.tagonn.app.utils.NetworkUtils
import com.tagonn.app.utils.PermissionUtils

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var webViewClient: TagonnWebViewClient
    private lateinit var webChromeClient: TagonnWebChromeClient
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            loadWebsite()
        } else {
            showPermissionDeniedDialog()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupWebView()
        setupSwipeRefresh()
        setupErrorHandling()
        
        if (checkPermissions()) {
            loadWebsite()
        } else {
            requestPermissions()
        }
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }
    
    private fun setupWebView() {
        webViewClient = TagonnWebViewClient(this)
        webChromeClient = TagonnWebChromeClient(this)
        
        binding.webView.apply {
            webViewClient = this@MainActivity.webViewClient
            webChromeClient = this@MainActivity.webChromeClient
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                userAgentString = userAgentString + " TagonnApp/1.0"
                
                // Enable modern web features
                mediaPlaybackRequiresUserGesture = false
                setGeolocationEnabled(true)
                
                // Cache settings
                cacheMode = WebSettings.LOAD_DEFAULT
                setAppCacheEnabled(true)
                setAppCachePath(cacheDir.absolutePath)
            }
        }
    }
    
    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.webView.reload()
        }
    }
    
    private fun setupErrorHandling() {
        binding.btnRetry.setOnClickListener {
            hideError()
            loadWebsite()
        }
    }
    
    private fun loadWebsite() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showError()
            return
        }
        
        hideError()
        binding.webView.loadUrl(getString(R.string.base_url))
    }
    
    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
    }
    
    private fun hideError() {
        binding.errorLayout.visibility = View.GONE
        binding.webView.visibility = View.VISIBLE
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
    }
    
    private fun checkPermissions(): Boolean {
        return PermissionUtils.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    private fun requestPermissions() {
        requestPermissionLauncher.launch(PermissionUtils.REQUIRED_PERMISSIONS)
    }
    
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage("Some permissions are required for the app to function properly. Please grant them in Settings.")
            .setPositiveButton("Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> {
                binding.webView.reload()
                true
            }
            R.id.action_share -> {
                shareCurrentPage()
                true
            }
            R.id.action_open_browser -> {
                openInBrowser()
                true
            }
            R.id.action_rate_app -> {
                rateApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun shareCurrentPage() {
        val url = binding.webView.url ?: getString(R.string.base_url)
        val shareText = getString(R.string.share_text, url)
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        startActivity(Intent.createChooser(intent, "Share via"))
    }
    
    private fun openInBrowser() {
        val url = binding.webView.url ?: getString(R.string.base_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    
    private fun rateApp() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.rate_app_url)))
        startActivity(intent)
    }
    
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
    
    fun onPageStarted() {
        showLoading(true)
        binding.swipeRefreshLayout.isRefreshing = false
    }
    
    fun onPageFinished() {
        showLoading(false)
        binding.swipeRefreshLayout.isRefreshing = false
    }
    
    fun onReceivedError() {
        showLoading(false)
        binding.swipeRefreshLayout.isRefreshing = false
        showError()
    }
}