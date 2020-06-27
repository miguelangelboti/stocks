package com.miguelangelboti.stocks.ui.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.observe
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.extensions.clearErrorWhenTextChanges
import com.miguelangelboti.stocks.utils.extensions.observeEvent
import com.miguelangelboti.stocks.utils.extensions.setErrorText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_order.autoCompleteTextView
import kotlinx.android.synthetic.main.activity_add_order.button
import kotlinx.android.synthetic.main.activity_add_order.priceEditText
import kotlinx.android.synthetic.main.activity_add_order.stocksEditText
import kotlinx.android.synthetic.main.activity_add_order.symbolEditText

@AndroidEntryPoint
class AddOrderActivity : AppCompatActivity() {

    private val viewModel by viewModels<AddOrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        symbolEditText.requestFocus()
        symbolEditText.clearErrorWhenTextChanges()
        stocksEditText.clearErrorWhenTextChanges()
        priceEditText.clearErrorWhenTextChanges()

        viewModel.isSymbolValid.observe(this) { symbolEditText.setErrorText(R.string.symbol_not_valid, !it) }
        viewModel.isStocksValid.observe(this) { stocksEditText.setErrorText(R.string.stocks_number_not_valid, !it) }
        viewModel.isPriceValid.observe(this) { priceEditText.setErrorText(R.string.stock_price_not_valid, !it) }
        viewModel.foundSymbols.observe(this) { setAutoCompleteItems(it) }
        viewModel.finish.observeEvent(this) { finish() }
        button.setOnClickListener {
            viewModel.addStock(
                symbol = symbolEditText.editText?.text.toString(),
                stocks = stocksEditText.editText?.text.toString(),
                price = priceEditText.editText?.text.toString()
            )
        }

        autoCompleteTextView.setAdapter(AutoCompleteListAdapter(this))
        autoCompleteTextView.doOnTextChanged { text, _, before, count ->
            // TODO: Improve this the item selection, some times it fails.
            // If before and count are the same, this change comes from item selection from dropdown.
            if (before == count) {
                stocksEditText.requestFocus()
                return@doOnTextChanged
            }
            onAutoCompleteTextChanged(text.toString())
        }
    }

    private fun onAutoCompleteTextChanged(text: String) {
        if (text.length < MINIMUM_CHARACTERS) {
            autoCompleteTextView.dismissDropDown()
            return
        }
        viewModel.searchSymbol(text)
    }

    private fun setAutoCompleteItems(items: List<Stock>) {
        autoCompleteTextView.setAdapter(AutoCompleteListAdapter(this, items.map { stock -> stock.symbol }))
        autoCompleteTextView.showDropDown()
    }

    companion object {
        private const val MINIMUM_CHARACTERS = 3
    }
}
