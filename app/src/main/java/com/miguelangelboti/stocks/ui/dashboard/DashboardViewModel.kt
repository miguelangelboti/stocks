package com.miguelangelboti.stocks.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miguelangelboti.stocks.data.StocksRepository
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.entities.getProfitability
import com.miguelangelboti.stocks.utils.extensions.format
import com.miguelangelboti.stocks.utils.extensions.sumByFloat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardViewModel @ViewModelInject constructor(
    repository: StocksRepository
) : ViewModel() {
    val profitabilityEuros: LiveData<String> = repository.getStocks().getProfitability("EUR").asLiveData()
    val profitabilityDollars: LiveData<String> = repository.getStocks().getProfitability("USD").asLiveData()
}

private fun Flow<List<Stock>>.getProfitability(currency: String): Flow<String> {
    return map { stocks -> stocks.filter { it.currency == currency }.sumByFloat { it.getProfitability() }.format() }
}
