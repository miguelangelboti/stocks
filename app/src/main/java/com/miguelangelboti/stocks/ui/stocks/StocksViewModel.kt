package com.miguelangelboti.stocks.ui.stocks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.utils.event.VoidEvent
import kotlinx.coroutines.launch

class StocksViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StocksRepository(getApplication())

    // val orders = liveData(Dispatchers.IO) { emit(repository.getOrders()) }
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _stopRefresh = MutableLiveData<VoidEvent>()
    val stopRefresh: LiveData<VoidEvent> = _stopRefresh

    init {
        viewModelScope.launch {
            _orders.value = repository.getOrders()
        }
    }

    fun refresh() = viewModelScope.launch {
        _orders.value = repository.getOrders()
        _stopRefresh.value = VoidEvent()
    }
}
