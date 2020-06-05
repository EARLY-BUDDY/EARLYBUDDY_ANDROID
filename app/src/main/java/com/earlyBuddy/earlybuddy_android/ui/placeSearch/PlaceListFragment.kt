package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication

import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding

class PlaceListFragment : BaseFragment<FragmentPlaceListBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_place_list
    override lateinit var viewModel: PlaceSearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(getViewModelStoreOwner(), ViewModelFactory()).get(PlaceSearchViewModel::class.java)
        viewModel = ViewModelProvider(getViewModelStoreOwner(), ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)

        setRv()
        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            Log.e("검색 리스트 observe", "실행실행~~₩")
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .notifyDataSetChanged()
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun Fragment.getViewModelStoreOwner(): ViewModelStoreOwner = try {
        requireActivity()
    } catch (e: IllegalStateException) {
        this
    }

    fun setRv(){
        Log.e("검색 리스트 setRv", "실행실행~~₩")
        viewDataBinding.fragPlaceListRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val layoutResID: Int
                        get() = R.layout.item_place_list
                    override val listener: OnItemClickListener?
                        get() = null
                }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
