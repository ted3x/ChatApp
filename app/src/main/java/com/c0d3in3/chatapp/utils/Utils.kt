package com.c0d3in3.chatapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate() : String{
    val date = Date(this)
    val format = SimpleDateFormat("MMM dd, HH:mm", Locale("ka", "Georgia"))
    return format.format(date)
}