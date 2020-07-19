package com.miguelangelboti.stocks.ui.stocks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.event.VoidEvent
import kotlinx.coroutines.launch

class StocksViewModel @ViewModelInject constructor(
    private val repository: StocksRepository
) : ViewModel() {

    private val _stopRefresh = MutableLiveData<VoidEvent>()
    val stopRefresh: LiveData<VoidEvent> = _stopRefresh

    val stocks: LiveData<List<Stock>> = repository.getStocks().asLiveData()

    fun refresh() = viewModelScope.launch {
        // TODO: Remove this logic, stock prices must be updated automatically.
        repository.update()
        _stopRefresh.value = VoidEvent()
    }
}
