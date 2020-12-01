package com.c0d3in3.chatapp.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c0d3in3.chatapp.Constants.MESSAGES_DATA_UPDATED
import com.c0d3in3.chatapp.Constants.MESSAGE_MODEL_KEY
import com.c0d3in3.chatapp.MessageReceiver

import com.c0d3in3.chatapp.R
import com.c0d3in3.chatapp.model.Message
import com.c0d3in3.chatapp.model.MessageOwner
import com.c0d3in3.chatapp.ui.adapter.ChatAdapter
import kotlinx.android.synthetic.main.chat_fragment.*

class ChatFragment(private val messageOwner: MessageOwner) : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: ChatAdapter
    private lateinit var messageReceiver: MessageReceiver
    private lateinit var imm: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setClickListeners()

        adapter = ChatAdapter(messageOwner)
        chatLV.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            chatLV.smoothScrollToPosition(it.size)
        })
    }

    override fun onResume() {
        super.onResume()
        messageReceiver = MessageReceiver {
            if (it.messageOwner != this.messageOwner) viewModel.addNewMessage(it)
        }
        requireActivity().registerReceiver(messageReceiver, IntentFilter(MESSAGES_DATA_UPDATED))
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(messageReceiver)
    }

    private fun setClickListeners() {
        sendMessageButton.setOnClickListener {
            val message = messageET.text.toString()
            if (message.isBlank()) Toast.makeText(
                requireContext(),
                getString(R.string.message_is_empty),
                Toast.LENGTH_SHORT
            ).show()
            else {
                sendMessage(message)
                messageET.text.clear()
                messageET.clearFocus()
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            }
        }
    }

    private fun sendMessage(messageText: String) {
        val message = Message(messageText, messageOwner, System.currentTimeMillis())
        viewModel.addNewMessage(message)

        val intent = Intent(MESSAGES_DATA_UPDATED)
        intent.putExtra(MESSAGE_MODEL_KEY, message)
        requireActivity().sendBroadcast(intent)
    }

}
