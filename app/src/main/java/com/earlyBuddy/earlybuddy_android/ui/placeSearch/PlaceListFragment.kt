package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication

import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceListRepository
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPlaceBinding
import kotlinx.android.synthetic.main.fragment_place_list.*

class PlaceListFragment : BaseFragment<FragmentPlaceListBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_place_list
    override val viewModel: PlaceSearchViewModel = PlaceSearchViewModel(repository = PlaceSearchRepository())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRv()

        viewModel.placeList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.e("검색 리스트 observe", "실행실행~~₩")
            fragtv.setText(it.get(0).placeName)
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .notifyDataSetChanged()
        })
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
            layoutManager = LinearLayoutManager(EarlyBuddyApplication.getGlobalApplicationContext())
        }
    }

    //    fun setRv() {
//        viewDataBinding.actStartPlaceSearchRv.apply {
//            adapter =
//                object : BaseRecyclerViewAdapter<PlaceSearch, ItemRecentPlaceBinding>() {
//                    override val layoutResID: Int
//                        get() = R.layout.item_recent_place
//                    override val bindingVariableId: Int
//                        get() = BR.placeRes
//                    override val listener: OnItemClickListener?
//                        get() = onClickListener
//
//                }
//            layoutManager = LinearLayoutManager(this@StartPlaceSearchActivity)
//        }
//
//        viewModel.placeList.observe(this, Observer {
//            Log.e("observe 실행", "실행햇다")
//            (viewDataBinding.actStartPlaceSearchRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemRecentPlaceBinding>)
//                .replaceAll(it)
//            (viewDataBinding.actStartPlaceSearchRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemRecentPlaceBinding>)
//                .notifyDataSetChanged()
//        })
//    }
}
