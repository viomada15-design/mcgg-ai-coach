package com.vio.mcggcoach.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder

class ScreenCaptureService : Service() {
    companion object {
        const val EXTRA_RESULT_CODE = "result_code"
        const val EXTRA_RESULT_DATA = "result_data"
        private const val CHANNEL_ID = "mcgg_capture"
        private const val NOTIFICATION_ID = 101
    }

    private var projection: MediaProjection? = null
    private var reader: ImageReader? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        val notification = android.app.Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("MCGG AI Coach")
            .setContentText("Analyzing visible game state")
            .setSmallIcon(android.R.drawable.ic_menu_view)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val code = intent?.getIntExtra(EXTRA_RESULT_CODE, ActivityResultCodes.CANCELED)
            ?: ActivityResultCodes.CANCELED
        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent?.getParcelableExtra(EXTRA_RESULT_DATA, Intent::class.java)
        } else {
            @Suppress("DEPRECATION") intent?.getParcelableExtra(EXTRA_RESULT_DATA)
        }
        if (code == ActivityResultCodes.OK && data != null) {
            val manager = getSystemService(MediaProjectionManager::class.java)
            projection = manager.getMediaProjection(code, data)
            // Phase 1 scaffold only. Next step creates an ImageReader/VirtualDisplay and
            // samples frames only when the board/shop changes, not continuously at 30 FPS.
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        reader?.close()
        projection?.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(NotificationChannel(
            CHANNEL_ID, "Screen analysis", NotificationManager.IMPORTANCE_LOW
        ))
    }

    private object ActivityResultCodes {
        const val OK = -1
        const val CANCELED = 0
    }
}
