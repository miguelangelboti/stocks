package com.miguelangelboti.stocks.ui.stocks

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

class StocksViewModel @ViewModelInject constructor(
    private val repository: StocksRepository
) : ViewModel() {

    // val orders = liveData(Dispatchers.IO) { emit(repository.getOrders()) }
    private val _stocks = MutableLiveData<List<Stock>>()
    val stocks: LiveData<List<Stock>> = _stocks

    private val _stopRefresh = MutableLiveData<VoidEvent>()
    val stopRefresh: LiveData<VoidEvent> = _stopRefresh

    init {
        viewModelScope.launch {
            _stocks.value = repository.getStocks()
        }
    }

    fun refresh() = viewModelScope.launch {
        _stocks.value = repository.getStocks()
        _stopRefresh.value = VoidEvent()
    }
}
