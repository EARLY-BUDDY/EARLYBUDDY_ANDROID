package com.earlyBuddy.earlybuddy_android.ui.myPage.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityMyPageBinding
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_my_page
    override val viewModel: MyPageViewModel by viewModel()
    lateinit var myPageAdapter : MyPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myPageAdapter =
            MyPageAdapter(object :
                MyPageViewHolder.MyPageItemClickListener {
                override fun itemClick(position: Int) {
                    Toast.makeText(
                        this@MyPageActivity,
                        myPageAdapter.myPageItemData[position].title,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@MyPageActivity,myPageAdapter.myPageItemData[position].goTo!!::class.java)
                    startActivity(intent)
                }
            })
        viewDataBinding.actMyRvMenu.adapter = myPageAdapter

    }
}