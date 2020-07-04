package com.miguelangelboti.stocks.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.event.VoidEvent
import kotlinx.coroutines.launch

class OrdersViewModel @ViewModelInject constructor(
    private val repository: StocksRepository
) : ViewModel() {

    // val orders = liveData(Dispatchers.IO) { emit(repository.getOrders()) }
    private val _stock = MutableLiveData<Stock>()
    val stock: LiveData<Stock> = _stock

    private val _stopRefresh = MutableLiveData<VoidEvent>()
    val stopRefresh: LiveData<VoidEvent> = _stopRefresh

    fun init(stockId: Int) {
        viewModelScope.launch {
            _stock.value = repository.getStock(stockId)
        }
    }

    fun refresh(stockId: Int) = viewModelScope.launch {
        _stock.value = repository.getStock(stockId)
        _stopRefresh.value = VoidEvent()
    }
}
