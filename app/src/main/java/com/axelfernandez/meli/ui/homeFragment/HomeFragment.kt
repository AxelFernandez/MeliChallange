package com.axelfernandez.meli.ui.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.axelfernandez.meli.R
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.api.RetrofitBuilder
import com.axelfernandez.meli.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.concurrent.atomic.AtomicBoolean

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_input.searchClickListener {
            if (search_input.checkIfTextIsNullOrEmpty()) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultFragment(search_input.getTextSearched()))
            }
        }

    }


}