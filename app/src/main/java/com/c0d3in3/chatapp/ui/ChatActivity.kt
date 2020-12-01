package com.c0d3in3.chatapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c0d3in3.chatapp.R
import com.c0d3in3.chatapp.model.MessageOwner

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initFragment(R.id.firstFragment, MessageOwner.FIRST_PERSON)
        initFragment(R.id.secondFragment, MessageOwner.SECOND_PERSON)
    }

    private fun initFragment(fragmentId: Int, messageOwner: MessageOwner) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentId, ChatFragment(messageOwner)).commit()
    }
}
