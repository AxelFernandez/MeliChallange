package com.axelfernandez.meli.ui.resultFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.axelfernandez.meli.R
import com.axelfernandez.meli.adapters.ResultAdapter
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.api.RetrofitBuilder
import com.axelfernandez.meli.utils.Status
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.no_conection_or_not_found_layout.view.*
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
            ResultViewModel.Factory(ApiHelper(RetrofitBuilder.buildService()),this,savedInstanceState)
        ).get(ResultViewModel::class.java)
        updateAppBarStatus()
        val search = viewModel.querySaved?:ResultFragmentArgs.fromBundle(arguments ?: return).query

        viewModel.searchItem(search)
        viewModel.items.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showLoading(true)
                }

                Status.ERROR -> {
                    Snackbar.make(view, getString(R.string.error_message), Snackbar.LENGTH_LONG)
                        .show()
                    showLoading(false)
                    rv.isVisible = false
                    problem_layout.isVisible = true
                }
                Status.EMPTY ->{
                    showLoading(false)
                    rv.isVisible = false
                    problem_layout.isVisible = true
                    problem_layout.error_image.setImageResource(R.drawable.ic_baseline_search_off_24)
                    problem_layout.error_label.text = getString(R.string.search_not_found)
                }

                Status.SUCCESS -> {
                    val data = it.data ?: return@Observer
                    viewModel.querySaved = data.query
                    appbarComponent.title = getString(R.string.results_from_query, it.data.query)
                    showLoading(false)
                    problem_layout.isVisible = false
                    rv.let {recycler->
                        recycler.isVisible = true
                        recycler.layoutManager = LinearLayoutManager(requireContext())
                        recycler.adapter = ResultAdapter(data.results, requireContext()) {item ->
                            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToItemDetail(item.id))
                        }
                    }

                }
            }
        })

        appbarComponent.searchSetOnClickListener {
            viewModel.searchItem(it)
        }
    }

    override fun onPause() {
        super.onPause()
        //Save the state when the mobile is rotating or a new Fragment is open
        viewModel.titleStatus = appbarComponent.showTitle

    }
    private fun showLoading(show: Boolean){
        progress_circular.isVisible = show
        searching_text.isVisible = show
    }
    private fun updateAppBarStatus(){
        appbarComponent.showBackArrow = viewModel.titleStatus
        appbarComponent.showTitle = viewModel.titleStatus
    }

}