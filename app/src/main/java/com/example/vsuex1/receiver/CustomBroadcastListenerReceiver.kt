package com.example.vsuex1.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CustomBroadcastListenerReceiver : BroadcastReceiver() {
    private var surfMessage = ""

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ru.shalkoff.vsu_lesson2_2024.SURF_ACTION") {
            surfMessage = intent.getStringExtra("message").toString()
            Log.i("surf_action_message", "Получено широковещательное сообщение: $surfMessage")
            Toast.makeText(context, "Получено широковещательное сообщение: $surfMessage", Toast.LENGTH_SHORT).show()
        }
    }

    fun getSurfMessageValue():String{
        return surfMessage
    }
}