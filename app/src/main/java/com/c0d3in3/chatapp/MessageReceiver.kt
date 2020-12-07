package com.c0d3in3.chatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.c0d3in3.chatapp.Constants.MESSAGE_MODEL_KEY
import com.c0d3in3.chatapp.model.Message

class MessageReceiver(private val messageReceived : (Message) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getParcelableExtra<Message>(MESSAGE_MODEL_KEY)
        messageReceived(message!!)
    }
}