package com.miguelangelboti.stocks.utils

private val currencies = hashMapOf(
    Pair("EUR", "€"),
    Pair("USD", "$")
)

fun getCurrencySymbol(literal: String, default: String = ""): String {
    return currencies[literal] ?: default
}
