package com.earlyBuddy.earlybuddy_android.ui.myPage.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityMyPageBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_my_page
    override val viewModel: MyPageViewModel by viewModel()
    lateinit var myPageAdapter : MyPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actAccountManageTopTitle.actTopTitleTvTitle.text = "마이페이지"

        viewDataBinding.actAccountManageTopTitle.actTopTitleIvBack.onlyOneClickListener {
            finish()
        }
        myPageAdapter =
            MyPageAdapter(object :
                MyPageViewHolder.MyPageItemClickListener {
                override fun itemClick(position: Int) {
//                    Toast.makeText(
//                        this@MyPageActivity,
//                        myPageAdapter.myPageItemData[position].title,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    val intent = Intent(
                        this@MyPageActivity,
                        myPageAdapter.myPageItemData[position].goTo!!::class.java
                    )
                    Log.e("clco", myPageAdapter.myPageItemData[position].title)
                    intent.putExtra("title", myPageAdapter.myPageItemData[position].title)
                    startActivity(intent)
                }
            })
        viewDataBinding.actMyRvMenu.adapter = myPageAdapter

    }
}