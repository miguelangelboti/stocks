package com.miguelangelboti.stocks.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.miguelangelboti.stocks.utils.event.Event
import com.miguelangelboti.stocks.utils.event.VoidEvent

inline fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, crossinline onChanged: (T) -> Unit) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onChanged) })
}

inline fun LiveData<VoidEvent>.observeEvent(owner: LifecycleOwner, crossinline onChanged: () -> Unit) {
    observe(owner, Observer { if (!it.hasBeenHandled()) onChanged() })
}
