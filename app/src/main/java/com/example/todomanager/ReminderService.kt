package com.example.todomanager

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

class ReminderService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Reminder Service gestartet", Toast.LENGTH_SHORT).show()

        startLoggingTask()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Reminder Service gestoppt", Toast.LENGTH_SHORT).show()

        stopLoggingTask()
    }

    private fun startLoggingTask() {
        runnable = kotlinx.coroutines.Runnable {
            Log.d("ReminderService", "Service is still running ...")
            handler.postDelayed(runnable, 5000)
        }
        handler.post(runnable)
    }

    private fun stopLoggingTask() {
        handler.removeCallbacks(runnable)
    }

}