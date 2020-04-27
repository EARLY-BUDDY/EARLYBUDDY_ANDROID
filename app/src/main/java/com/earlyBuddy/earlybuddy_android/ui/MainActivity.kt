package com.earlyBuddy.earlybuddy_android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.databinding.ActivityMainBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemMainBinding
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    // databinding객체(viewDataBinding)와 viewModel객체(viewModel) 생성
    override val layoutResID: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel
        get() = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        setRv()
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val name = (item as PlaceSearch).placeName
            Toast.makeText(this@MainActivity, "$name 에 가시나요?", Toast.LENGTH_SHORT).show()
        }


    }

    fun setRv() {
        viewDataBinding.actMainRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemMainBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_main
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val listener: OnItemClickListener?
                        get() = onClickListener

                }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.placeList.observe(this, Observer {
            (viewDataBinding.actMainRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemMainBinding>)
                .replaceAll(it)
            (viewDataBinding.actMainRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemMainBinding>)
                .notifyDataSetChanged()
        })
    }

}
