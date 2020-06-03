package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentRecentPlaceBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPlaceBinding
import org.koin.android.viewmodel.ext.android.viewModel



class RecentPlaceFragment : BaseFragment<FragmentRecentPlaceBinding, RecentPlaceViewModel>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRv()
    }

    override val layoutResID: Int
        get() = R.layout.fragment_recent_place
    override val viewModel: RecentPlaceViewModel by viewModel()

    fun setRv() {
        viewDataBinding.fragRecentPlaceRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemRecentPlaceBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_recent_place
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val listener: OnItemClickListener?
                        get() = null
                }
            layoutManager = LinearLayoutManager(activity)
        }
        viewModel.recentPlaceList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.e("최근검색", "최근검색 실행")
            (viewDataBinding.fragRecentPlaceRv.adapter as BaseRecyclerViewAdapter<RecentPlaceEntity, ItemRecentPlaceBinding>)
                .replaceAll(it.value)
            (viewDataBinding.fragRecentPlaceRv.adapter as BaseRecyclerViewAdapter<RecentPlaceEntity, ItemRecentPlaceBinding>)
                .notifyDataSetChanged()
        })
    }
}
