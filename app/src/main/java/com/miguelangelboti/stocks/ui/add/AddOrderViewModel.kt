package com.miguelangelboti.stocks.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.OrderRequest
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.event.VoidEvent
import kotlinx.coroutines.launch

class AddOrderViewModel @ViewModelInject constructor(
    private val repository: StocksRepository
) : ViewModel() {

    private val _isSymbolValid = MutableLiveData<Boolean>()
    val isSymbolValid: LiveData<Boolean> = _isSymbolValid

    private val _isStocksValid = MutableLiveData<Boolean>()
    val isStocksValid: LiveData<Boolean> = _isStocksValid

    private val _isPriceValid = MutableLiveData<Boolean>()
    val isPriceValid: LiveData<Boolean> = _isPriceValid

    private val _foundSymbols = MutableLiveData<List<Stock>>()
    val foundSymbols: LiveData<List<Stock>> = _foundSymbols

    private val _finish = MutableLiveData<VoidEvent>()
    val finish: LiveData<VoidEvent> = _finish

    fun addStock(symbol: String, stocks: String, price: String) = viewModelScope.launch {

        val stocksParsed = stocks.toFloatOrNull()
        val priceParsed = price.toFloatOrNull()

        _isSymbolValid.value = symbol.isNotBlank()
        _isStocksValid.value = stocksParsed != null
        _isPriceValid.value = priceParsed != null

        if (isSymbolValid.value == true && stocksParsed != null && priceParsed != null) {
            repository.addOrder(OrderRequest(symbol, stocksParsed, priceParsed))
            _finish.value = VoidEvent()
        }
    }

    fun searchSymbol(symbol: String) = viewModelScope.launch {
        _foundSymbols.value = repository.searchSymbol(symbol)
    }
}
