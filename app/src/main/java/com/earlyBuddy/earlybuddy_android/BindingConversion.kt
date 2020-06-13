package com.earlyBuddy.earlybuddy_android

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("placeName", "address")
fun checkNull(view : TextView, placeName : String?, address: String?){
    Log.e("bindingAdapter",placeName)
    if(placeName==null){
        view.text = address
    }else{
        view.text = placeName
    }
}