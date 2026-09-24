package com.vio.mcggcoach.service

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {
    private lateinit var wm: WindowManager
    private var overlay: TextView? = null

    override fun onCreate() {
        super.onCreate()
        wm = getSystemService(WindowManager::class.java)
        overlay = TextView(this).apply {
            text = "🧠 AI COACH\nObserving…"
            textSize = 15f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.argb(210, 22, 25, 32))
            setPadding(24, 18, 24, 18)
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 24
            y = 120
        }
        wm.addView(overlay, params)
    }

    fun updateCoachText(text: String) { overlay?.text = "🧠 AI COACH\n$text" }

    override fun onDestroy() {
        overlay?.let { wm.removeView(it) }
        overlay = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
