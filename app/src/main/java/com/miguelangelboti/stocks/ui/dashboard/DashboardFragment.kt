package com.miguelangelboti.stocks.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.miguelangelboti.stocks.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.textView02
import kotlinx.android.synthetic.main.fragment_dashboard.textView04

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    companion object {
        val TAG = DashboardFragment::class.java.simpleName
        fun newInstance() = DashboardFragment()
    }

    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profitabilityEuros.observe(viewLifecycleOwner) { textView02.text = it }
        viewModel.profitabilityDollars.observe(viewLifecycleOwner) { textView04.text = it }
    }
}
