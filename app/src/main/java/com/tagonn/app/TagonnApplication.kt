package com.tagonn.app

import android.app.Application
import android.content.Context
import android.webkit.WebView

class TagonnApplication : Application() {
    
    companion object {
        lateinit var instance: TagonnApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize WebView for better performance
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val processInfo = android.app.ActivityManager.RunningAppProcessInfo()
            android.app.ActivityManager.getMyMemoryState(processInfo)
            if (packageName == processInfo.processName) {
                WebView.setDataDirectorySuffix(processInfo.processName)
            }
        }
    }
    
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}