package com.c0d3in3.chatapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.c0d3in3.chatapp.R
import com.c0d3in3.chatapp.model.Message
import com.c0d3in3.chatapp.model.MessageOwner
import com.c0d3in3.chatapp.utils.toDate
import kotlinx.android.synthetic.main.not_self_message_item.view.*
import kotlinx.android.synthetic.main.self_message_item.view.*

class ChatAdapter(
    private val messageOwner: MessageOwner
) : BaseAdapter() {

    companion object {
        private const val SELF_MESSAGE = 1000
        private const val NOT_SELF_MESSAGE = 1001
    }

    private var messages = listOf<Message>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val myView: View
        val model = messages[position]
        when (getItemViewType(position)) {
            SELF_MESSAGE -> {
                myView = LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.self_message_item, parent, false)
                myView.apply{
                    selfTV.text = model.message
                    selfDateTV.text = model.messageTimestamp.toDate()
                }

            }
            else -> {
                myView = LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.not_self_message_item, parent, false)
                myView.apply{
                    notSelfTV.text = model.message
                    notSelfDateTV.text = model.messageTimestamp.toDate()
                }
            }
        }
        return myView
    }

    override fun getItem(position: Int) = messages[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = messages.size

    override fun getItemViewType(position: Int) =
        if (messages[position].messageOwner == messageOwner) SELF_MESSAGE else NOT_SELF_MESSAGE


    fun setList(messagesList: List<Message>) {
        messages = messagesList
        notifyDataSetChanged()
    }
}
