package com.example.todomanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val status = intent?.getIntExtra("status", -1)
        val isCharged = status == android.os.BatteryManager.BATTERY_STATUS_CHARGING

        if (isCharged) {
            Toast.makeText(context, "Device is charging", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Device is not charging", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun registerReceiver(context: Context): BatteryReceiver {
            val receiver = BatteryReceiver()
            val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

            context.registerReceiver(receiver, filter)

            return receiver
        }

        fun unregisterReceiver(context: Context, receiver: BatteryReceiver) {
            context.unregisterReceiver(receiver)
        }
    }

}