package com.miguelangelboti.stocks.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.miguelangelboti.stocks.R

class DashboardFragment : Fragment() {

    companion object {
        val TAG = DashboardFragment::class.java.simpleName
        fun newInstance() = DashboardFragment()
    }

    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}
