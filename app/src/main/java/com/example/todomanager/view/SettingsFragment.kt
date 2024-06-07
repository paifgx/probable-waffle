package com.example.todomanager.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.todomanager.R
import com.example.todomanager.ReminderService

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val startServiceButton: Button = view.findViewById(R.id.start_service_button)
        val stopServiceButton: Button = view.findViewById(R.id.stop_service_button)

        startServiceButton.setOnClickListener {
            val intent = Intent(activity, ReminderService::class.java).apply {
                putExtra("reminder_message", "Erinnerung: Zeit für eine Pause!")
            }
            activity?.startService(intent)
        }

        stopServiceButton.setOnClickListener {
            val intent = Intent(activity, ReminderService::class.java)
            activity?.stopService(intent)
        }

        return view
    }

}