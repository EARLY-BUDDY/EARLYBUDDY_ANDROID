package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import com.earlyBuddy.earlybuddy_android.databinding.ActivityEndPlaceSearchBinding
import kotlinx.android.synthetic.main.activity_end_place_search.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_start_place_search.*

class EndPlaceSearchActivity : BaseActivity<ActivityEndPlaceSearchBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_end_place_search
    override val viewModel: PlaceSearchViewModel = PlaceSearchViewModel(repository = PlaceSearchRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

}
