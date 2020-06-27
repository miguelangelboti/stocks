package com.miguelangelboti.stocks.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.utils.event.VoidEvent
import kotlinx.coroutines.launch

class OrdersViewModel @ViewModelInject constructor(
    private val repository: StocksRepository
) : ViewModel() {

    // val orders = liveData(Dispatchers.IO) { emit(repository.getOrders()) }
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _stopRefresh = MutableLiveData<VoidEvent>()
    val stopRefresh: LiveData<VoidEvent> = _stopRefresh

    fun init(stockId: Int) {
        viewModelScope.launch {
            _orders.value = repository.getOrders(stockId)
        }
    }

    fun refresh(stockId: Int) = viewModelScope.launch {
        _orders.value = repository.getOrders(stockId)
        _stopRefresh.value = VoidEvent()
    }
}
