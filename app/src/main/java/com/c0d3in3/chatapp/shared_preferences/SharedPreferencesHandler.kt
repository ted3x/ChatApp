package com.c0d3in3.chatapp.shared_preferences

import android.content.Context
import com.c0d3in3.chatapp.App
import com.c0d3in3.chatapp.model.Message
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object SharedPreferencesHandler{

    private const val PREFERENCE_FILE_KEY = "com.c0d3in3.chatapp.PREFERENCE_FILE"
    private const val CHAT_KEY = "chat"
    private val sharedPref = App.instance.applicationContext.getSharedPreferences(
        PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
    )

    fun saveMessages(messages: List<Message>) {
        val listToJson = Gson().toJson(messages)
        sharedPref.edit().putString(CHAT_KEY, listToJson).apply()
    }

    fun getMessages() : List<Message>{
        val messagesJson = sharedPref.getString(CHAT_KEY, "")
        if(messagesJson!!.isEmpty()) return emptyList()
        val collectionType: Type =
            object : TypeToken<List<Message>>() {}.type
        return Gson().fromJson(messagesJson, collectionType) as List<Message>
    }
}