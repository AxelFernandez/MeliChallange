package com.axelfernandez.meli.ui.resultFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.axelfernandez.meli.R
import com.axelfernandez.meli.adapters.ResultAdapter
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.api.RetrofitBuilder
import com.axelfernandez.meli.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.result_fragment.*

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ResultViewModel.Factory(ApiHelper(RetrofitBuilder.buildService()))
        ).get(ResultViewModel::class.java)

        val arguments = ResultFragmentArgs.fromBundle(arguments ?: return)
        appbarComponent.title = getString(R.string.results_from_query, arguments.query)

        viewModel.searchItem(arguments.query)
        viewModel.items.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    Snackbar.make(view, getString(R.string.error_message), Snackbar.LENGTH_LONG)
                        .show()
                    showLoading(false)
                }
                Status.SUCCESS -> {
                    val data = it.data?.results ?: return@Observer
                    appbarComponent.title = it.data.query
                    showLoading(false)
                    rv.layoutManager = LinearLayoutManager(requireContext())
                    rv.adapter = ResultAdapter(data, requireContext()) {
                        Toast.makeText(requireContext(),it.id,Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        appbarComponent.searchSetOnClickListener {
            viewModel.searchItem(it)
        }
    }
    private fun showLoading(show: Boolean){
        progress_circular.isVisible = show
        searching_text.isVisible = show
    }
}