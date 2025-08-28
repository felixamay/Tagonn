package com.tagonn.app.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.webkit.*
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TagonnWebChromeClient(private val activity: MainActivity) : WebChromeClient() {
    
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var photoURI: Uri? = null
    
    private val getContent = activity.registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            filePathCallback?.onReceiveValue(arrayOf(uri))
        } else {
            filePathCallback?.onReceiveValue(null)
        }
        filePathCallback = null
    }
    
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        // Progress updates can be handled here if needed
    }
    
    override fun onPermissionRequest(request: PermissionRequest?) {
        request?.grant(request.resources)
    }
    
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        if (this.filePathCallback != null) {
            this.filePathCallback?.onReceiveValue(null)
        }
        this.filePathCallback = filePathCallback
        
        val intent = fileChooserParams?.createIntent()
        if (intent != null) {
            try {
                getContent.launch("*/*")
                return true
            } catch (e: Exception) {
                filePathCallback?.onReceiveValue(null)
                this.filePathCallback = null
                return false
            }
        }
        
        return false
    }
    
    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                result?.confirm()
            }
            .setCancelable(false)
            .show()
        return true
    }
    
    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                result?.confirm()
            }
            .setNegativeButton("Cancel") { _, _ ->
                result?.cancel()
            }
            .setCancelable(false)
            .show()
        return true
    }
    
    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean {
        val input = EditText(activity)
        input.setText(defaultValue)
        
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                result?.confirm(input.text.toString())
            }
            .setNegativeButton("Cancel") { _, _ ->
                result?.cancel()
            }
            .setCancelable(false)
            .show()
        return true
    }
    
    override fun onGeolocationPermissionsShowPrompt(
        origin: String?,
        callback: GeolocationPermissions.Callback?
    ) {
        callback?.invoke(origin, true, false)
    }
}