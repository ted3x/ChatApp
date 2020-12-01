package com.c0d3in3.chatapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c0d3in3.chatapp.model.Message
import com.c0d3in3.chatapp.shared_preferences.SharedPreferencesHandler

class ChatViewModel : ViewModel() {
    val messages = MutableLiveData<MutableList<Message>>()

    init {
        getMessages()
    }


    fun addNewMessage(message: Message){
        val list = messages.value
        list?.add(message)
        messages.value = list
        saveMessages()
    }

    private fun getMessages(){
        messages.value = SharedPreferencesHandler.getMessages().toMutableList()
    }

    private fun saveMessages(){
        messages.value?.let { SharedPreferencesHandler.saveMessages(it) }
    }
}
