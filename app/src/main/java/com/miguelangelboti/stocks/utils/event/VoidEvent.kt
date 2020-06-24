package com.miguelangelboti.stocks.utils.event

class VoidEvent {

    private var hasBeenHandled = false

    fun hasBeenHandled() = if (hasBeenHandled) {
        true
    } else {
        hasBeenHandled = true
        false
    }
}
