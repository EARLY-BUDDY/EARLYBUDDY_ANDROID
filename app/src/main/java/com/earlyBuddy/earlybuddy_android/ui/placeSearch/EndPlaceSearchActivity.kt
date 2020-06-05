package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityEndPlaceSearchBinding
import kotlinx.android.synthetic.main.activity_end_place_search.*
import kotlinx.android.synthetic.main.activity_start_place_search.*

class EndPlaceSearchActivity : BaseActivity<ActivityEndPlaceSearchBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_end_place_search
    override lateinit var viewModel: PlaceSearchViewModel

    val recentPlaceFragment = RecentPlaceFragment()
    val placeListFragment = PlaceListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this ,ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)

        act_end_place_search_iv_cancel.setOnClickListener{
            act_end_place_search_et_search.text.clear()
        }

        act_end_place_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_end_place_search_et_search.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    fun getPlaceData(){
        val query = act_end_place_search_et_search.text.toString()
        viewModel.getPlaceSearchData(query)
    }

    fun textWatch(){
        act_end_place_search_et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                setFrag()
                if(act_end_place_search_et_search.text.isNotEmpty())
                    getPlaceData()
            }
        })
    }
}
