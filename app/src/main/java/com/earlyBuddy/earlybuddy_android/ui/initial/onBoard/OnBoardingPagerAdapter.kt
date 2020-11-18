package com.earlyBuddy.earlybuddy_android.ui.initial.onBoard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.earlyBuddy.earlybuddy_android.R
import kotlinx.android.synthetic.main.fragment_onboarding_page.view.*

class OnBoardingPagerAdapter : PagerAdapter() {
    val onBoardList = listOf<OnBoardingData>(
        OnBoardingData(
            "이제는 약속에 늦지마세요!",
            "도착할 시간을 설정하고 내가 원하는\n경로와 알림으로 일정에 늦지 않게 출발하세요!", R.drawable.img_onboarding_1
        ),
        OnBoardingData(
            "내 일정과 배차정보를 한눈에!",
            "오늘 일정이 무엇인지, 언제 출발해야할지\n얼리버디가 상황에 맞게 알려줄게요!", R.drawable.img_onboarding_2
        ),
        OnBoardingData(
            "캘린더로 모든 일정 관리까지!",
            "이동 경로부터 배차 알림까지,\n일정에 필요한 모든 정보를 관리할 수 있어요!", R.drawable.img_onboarding_3
        )
    )

    override fun getCount(): Int {
        return onBoardList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.fragment_onboarding_page, container, false)

        view.frag_on_board_title.text = onBoardList[position].title
        view.frag_on_board_desc.text = onBoardList[position].desc
        view.frag_on_board_img.setImageResource(onBoardList[position].image)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    
}

data class OnBoardingData(
    val title: String,
    val desc: String,
    val image: Int
)