package com.axelfernandez.meli.ui.itemDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.axelfernandez.meli.R
import com.axelfernandez.meli.adapters.ImageCarrouselAdapter
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.api.RetrofitBuilder
import com.axelfernandez.meli.databinding.ItemDetailFragmentBinding
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.ui.homeFragment.HomeFragmentDirections
import com.axelfernandez.meli.utils.Condition
import com.axelfernandez.meli.utils.Status
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.item_detail_fragment.*
import kotlinx.android.synthetic.main.item_detail_fragment.title

class ItemDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ItemDetailFragment()
    }

    private lateinit var viewModel: ItemDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,ItemDetailViewModel.Factory(ApiHelper(RetrofitBuilder.buildService()))).get(ItemDetailViewModel::class.java)
        val itemId = ItemDetailFragmentArgs.fromBundle(arguments?:return).id


        viewModel.getDetail(itemId).observe(viewLifecycleOwner, Observer {

            when(it.status){
                Status.LOADING->{
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)
                    hideAll()
                    no_connection_layout.isVisible = true
                }
                Status.SUCCESS -> {
                    showLoading(false)
                    val item = it.data?:return@Observer
                    bindUI(item)
                }
            }
        })
        viewModel.getDescription(itemId).observe(viewLifecycleOwner, Observer {
            when (it.status){
                Status.ERROR -> {
                    card_view_description.isVisible = false
                }
                Status.SUCCESS -> {
                    description.text = it.data?.plain_text
                }
            }
        })

        detail_search_again.searchClickListener {
            if (detail_search_again.checkIfTextIsNullOrEmpty()) {
                findNavController().navigate(ItemDetailFragmentDirections.actionItemDetailToResultFragment(detail_search_again.getTextSearched()))
            }
        }

    }

    private fun showLoading(value :Boolean){
        progress_circular.isVisible = value

    }
    private fun hideAll(){
        card_view_description.isVisible = false
        search_again.isVisible = false
    }

    private fun bindUI(item: Item){
        condition.text = Condition.valueOf(item.condition).getTranslation()
        title.text = item.title
        price.text = resources.getString(R.string.item_price, item.price.toInt().toString())
        rv_images.let {
            it.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            it.adapter = ImageCarrouselAdapter(item.pictures?:return, requireContext())
        }
    }

}