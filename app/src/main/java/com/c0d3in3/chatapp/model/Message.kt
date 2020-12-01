package com.c0d3in3.chatapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message (
    val message: String,
    val messageOwner: MessageOwner,
    val messageTimestamp: Long
): Parcelable