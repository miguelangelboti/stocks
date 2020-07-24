package com.miguelangelboti.stocks.utils.extensions

fun Float.format(decimals: Int = 2): String {
    return "%.${decimals}f".format(this)
}
