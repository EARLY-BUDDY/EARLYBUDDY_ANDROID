package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PlaceListFragment : BaseFragment<FragmentPlaceListBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_place_list
    override val viewModel: PlaceSearchViewModel by sharedViewModel()

    val placeResultFragment  = PlaceResultFragment()
    private var flag = 0
    var latitude = 0.0
    var longitude = 0.0
    val bundle = Bundle(1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flag = arguments!!.getInt("flag")
        latitude = arguments!!.getDouble("latitude")
        longitude = arguments!!.getDouble("longitude")
        setRv()
    }

    fun setRv(){
        viewDataBinding.fragPlaceListRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val layoutResID: Int
                        get() = R.layout.item_place_list
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .notifyDataSetChanged()
        })
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {

            val keyboard: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(requireView().windowToken, 0)

            val placeName = (item as PlaceSearch).placeName
            val address = item.addressName
            if(placeName == null){
                viewModel.getPlaceSearchData(address, longitude, latitude)
            }else
                viewModel.getPlaceSearchData(placeName, longitude, latitude)

            if(flag==1){
                requireActivity().currentFocus?.clearFocus()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_start_place_search_container,
                        placeResultFragment
                    ).commit()
                bundle.putInt("flag", 1)
                placeResultFragment.arguments = bundle
            }
            else if(flag==2){
                requireActivity().currentFocus?.clearFocus()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_end_place_search_container,
                        placeResultFragment
                    ).commit()
                bundle.putInt("flag", 2)
                placeResultFragment.arguments = bundle
            }
        }
    }
}
