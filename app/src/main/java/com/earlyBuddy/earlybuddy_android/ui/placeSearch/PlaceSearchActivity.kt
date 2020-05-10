package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPlaceSearchBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemActPlaceSearchBinding
import com.earlyBuddy.earlybuddy_android.util.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_place_search.*

class PlaceSearchActivity : BaseActivity<ActivityPlaceSearchBinding, PlaceSearchViewModel>() {

    var repository = PlaceSearchRepository()

    override val layoutResID: Int
        get() = R.layout.activity_place_search
    override val viewModel: PlaceSearchViewModel = PlaceSearchViewModel(repository = PlaceSearchRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        setRv()
        getPlaceData()
        textWatch()
        act_place_search_iv_cancel.setOnClickListener {
            val query = act_place_search_et_search.text.toString()
            viewModel.getPlaceSearchData(query)
        }
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val name = (item as PlaceSearch).placeName
            Toast.makeText(this@PlaceSearchActivity, "$name 에 가시나요?", Toast.LENGTH_SHORT).show()
        }
    }

    fun setRv() {
        viewDataBinding.actPlaceSearchRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemActPlaceSearchBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_act_place_search
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val listener: OnItemClickListener?
                        get() = onClickListener

                }
            layoutManager = LinearLayoutManager(this@PlaceSearchActivity)
        }

        Log.e("setRv 실행", "실행햇다")

        viewModel.placeList.observe(this, Observer {
            Log.e("observe 실행", "실행햇다")
            (viewDataBinding.actPlaceSearchRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemActPlaceSearchBinding>)
                .replaceAll(it)
            (viewDataBinding.actPlaceSearchRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemActPlaceSearchBinding>)
                .notifyDataSetChanged()
        })
    }

    fun getPlaceData(){
        Log.e("getPlaceData 실행", "실행햇다")
        val query = act_place_search_et_search.text.toString()
        viewModel.getPlaceSearchData(query)
    }

    fun textWatch(){
        act_place_search_et_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPlaceData()
            }
        })
    }
}
