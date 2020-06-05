package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentRecentPlaceBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPlaceBinding


class RecentPlaceFragment : BaseFragment<FragmentRecentPlaceBinding, RecentPlaceViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_recent_place
    override lateinit var viewModel: RecentPlaceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(getViewModelStoreOwner(), ViewModelProvider.NewInstanceFactory()).get(RecentPlaceViewModel::class.java)
        setRv()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun Fragment.getViewModelStoreOwner(): ViewModelStoreOwner = try {
        requireActivity()
    } catch (e: IllegalStateException) {
        this
    }

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
