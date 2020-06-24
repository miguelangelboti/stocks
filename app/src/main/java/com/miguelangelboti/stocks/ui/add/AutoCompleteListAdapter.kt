package com.miguelangelboti.stocks.ui.add

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes

class AutoCompleteListAdapter internal constructor(context: Context, items: List<String> = mutableListOf()) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items)
