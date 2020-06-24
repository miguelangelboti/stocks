package com.miguelangelboti.stocks.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

@SuppressLint("SimpleDateFormat")
fun getUtcDatetime(): Date {
    val string = getUtcDatetimeAsString()
    // This should not throws a ParseException.
    return SimpleDateFormat(DATE_FORMAT).parse(string)!!
}

@SuppressLint("SimpleDateFormat")
fun getUtcDatetimeAsString(): String {
    return SimpleDateFormat(DATE_FORMAT).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date())
}
