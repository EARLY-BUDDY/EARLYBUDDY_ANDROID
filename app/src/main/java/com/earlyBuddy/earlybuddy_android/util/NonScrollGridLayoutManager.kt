package com.earlyBuddy.earlybuddy_android.util

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class NonScrollGridLayoutManager(context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}