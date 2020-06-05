package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.*
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import com.earlyBuddy.earlybuddy_android.databinding.ActivityStartPlaceSearchBinding
import kotlinx.android.synthetic.main.activity_start_place_search.*

class StartPlaceSearchActivity : BaseActivity<ActivityStartPlaceSearchBinding, PlaceSearchViewModel>() {

    var repository = PlaceSearchRepository()
    val bundle = Bundle()

    val recentPlaceFragment = RecentPlaceFragment()
    val placeListFragment = PlaceListFragment()
    override val layoutResID: Int
        get() = R.layout.activity_start_place_search
    override lateinit var viewModel: PlaceSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = ViewModelProviders.of(this,ViewModelFactory()).get(PlaceSearchViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.act_start_place_search_container,
                RecentPlaceFragment()
            ).commit()
//        recentPlaceFragment.arguments = bundle

        viewDataBinding.vm = viewModel
//        setFrag()
        textWatch()
//        insertDB()
//        act_start_place_search_iv_cancel.setOnClickListener {
//            val query = act_start_place_search_et_search.text.toString()
//            viewModel.getPlaceSearchData(query)
//        }
        act_start_place_search_iv_cancel.setOnClickListener{
            act_start_place_search_et_search.text.clear()
        }
        act_start_place_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_start_place_search_et_search.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    fun setFrag(){
        val nowFrag = supportFragmentManager.findFragmentById(R.id.act_start_place_search_container)
        if(act_start_place_search_et_search.text.isEmpty()){
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.act_start_place_search_container,
                    recentPlaceFragment
                ).commit()
//            recentPlaceFragment.arguments = bundle

        } else if(act_start_place_search_et_search.text.isNotEmpty() && nowFrag!=placeListFragment){
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.act_start_place_search_container,
                    placeListFragment
                ).commit()
//            placeListFragment.arguments = bundle
        }
    }

    fun getPlaceData(){
//        Log.e("getPlaceData 실행", "실행햇다")
        val query = act_start_place_search_et_search.text.toString()
        viewModel.getPlaceSearchData(query)
    }

    fun textWatch(){
        act_start_place_search_et_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setFrag()
                if(act_start_place_search_et_search.text.isNotEmpty())
                    getPlaceData()
            }
        })
    }

//    fun insertDB(){
//        act_start_place_search_et_search.addTextChangedListener(object :TextWatcher{
//            override fun afterTextChanged(p0: Editable?) {
//                viewModel.insert(RecentPlaceEntity(placeName = act_start_place_search_et_search.text.toString()))
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        })
//    }
}
