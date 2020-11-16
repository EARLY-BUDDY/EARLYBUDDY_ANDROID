package com.earlyBuddy.earlybuddy_android.ui.initial.onBoard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        val adapter = OnBoardingPagerAdapter()
        act_on_board_pager.adapter = adapter

        act_on_board_tv_next.text = "다음으로"
        act_on_board_tv_next.onlyOneClickListener {
            act_on_board_pager.currentItem = 1
        }

        act_on_board_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                act_on_board_circle.selectDot(position)
                when (position) {
                    2 -> {

                        act_on_board_tv_next.text = "시작하기"
                        act_on_board_tv_next.onlyOneClickListener {
                            val intent = Intent(this@OnBoardActivity, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else -> {
                        act_on_board_tv_next.text = "다음으로"
                        act_on_board_tv_next.onlyOneClickListener {
                            act_on_board_pager.currentItem = position + 1
                        }
                    }
                }
//                Toast.makeText(this@OnBoardActivity, position.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        act_on_board_circle.createDotPanel(
            3,
            R.drawable.indicator_dot_off,
            R.drawable.indicator_dot_on,
            0
        )
    }
}