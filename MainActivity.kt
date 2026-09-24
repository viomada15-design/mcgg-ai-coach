package com.vio.mcggcoach

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.vio.mcggcoach.service.OverlayService
import com.vio.mcggcoach.service.ScreenCaptureService

class MainActivity : Activity() {
    private val captureRequest = 9001
    private lateinit var projectionManager: MediaProjectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectionManager = getSystemService(MediaProjectionManager::class.java)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 80, 48, 48)
            setBackgroundColor(Color.rgb(17, 19, 24))
        }
        root.addView(TextView(this).apply {
            text = "MCGG AI Coach — MVP 0.1"
            textSize = 26f
            setTextColor(Color.WHITE)
        })
        root.addView(TextView(this).apply {
            text = "Screen observer + floating strategy overlay.\nNo automatic taps or game modification."
            textSize = 16f
            setTextColor(Color.LTGRAY)
            setPadding(0, 24, 0, 32)
        })

        val overlayButton = Button(this).apply {
            text = "1. Enable overlay"
            setOnClickListener { ensureOverlayPermission() }
        }
        root.addView(overlayButton)

        val startButton = Button(this).apply {
            text = "2. Start coach"
            setOnClickListener { startCaptureFlow() }
        }
        root.addView(startButton)

        val stopButton = Button(this).apply {
            text = "Stop coach"
            setOnClickListener {
                stopService(Intent(this@MainActivity, ScreenCaptureService::class.java))
                stopService(Intent(this@MainActivity, OverlayService::class.java))
            }
        }
        root.addView(stopButton)
        setContentView(root)
    }

    private fun ensureOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")))
        } else {
            startService(Intent(this, OverlayService::class.java))
        }
    }

    private fun startCaptureFlow() {
        if (!Settings.canDrawOverlays(this)) {
            ensureOverlayPermission()
            return
        }
        startService(Intent(this, OverlayService::class.java))
        startActivityForResult(projectionManager.createScreenCaptureIntent(), captureRequest)
    }

    @Deprecated("Deprecated in Android SDK; kept for simple MVP compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == captureRequest && resultCode == RESULT_OK && data != null) {
            val intent = Intent(this, ScreenCaptureService::class.java).apply {
                putExtra(ScreenCaptureService.EXTRA_RESULT_CODE, resultCode)
                putExtra(ScreenCaptureService.EXTRA_RESULT_DATA, data)
            }
            startForegroundService(intent)
            moveTaskToBack(true)
        }
    }
}
