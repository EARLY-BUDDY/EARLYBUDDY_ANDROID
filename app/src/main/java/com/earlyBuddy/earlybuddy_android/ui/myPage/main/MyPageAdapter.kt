package com.earlyBuddy.earlybuddy_android.ui.myPage.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.model.MyPageItem
import com.earlyBuddy.earlybuddy_android.databinding.ItemMyPageMenuBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemMyPageMenuTopBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.initial.place.InitialPlaceActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.accountManagement.AccountManagementActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.license.LicenseActivity

class MyPageAdapter(private val myPageItemClickListener: MyPageViewHolder.MyPageItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val myPageItemData = listOf<MyPageItem>(
//        MyPageItem("전체 알림", null),
        MyPageItem("계정 관리", AccountManagementActivity()),
        MyPageItem("자주 가는 장소", InitialPlaceActivity()),
        MyPageItem("오픈소스 라이선스", LicenseActivity()),
        MyPageItem("이용약관", LicenseActivity()),
        MyPageItem("개인정보처리방침", LicenseActivity())
    )

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                1
            }
            else -> {
                0
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
//            1 -> {  // 첫번째 토글 알림 설정 창
//                val topItemBinding =
//                    ItemMyPageMenuTopBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                return MyPageTopViewHolder(
//                    topItemBinding
//                )
//            }
            else -> {
                val generalItemBinding =
                    ItemMyPageMenuBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MyPageViewHolder(
                    generalItemBinding,
                    myPageItemClickListener
                )

            }
        }
    }

    override fun getItemCount(): Int {
        return myPageItemData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageViewHolder -> {
                holder.bind(myPageItemData[position])
            }
            is MyPageTopViewHolder -> {
                holder.bind(myPageItemData[position])
            }
        }
    }
}

class MyPageViewHolder(
    val binding: ItemMyPageMenuBinding,
    private val myPageItemClickListener: MyPageItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface MyPageItemClickListener {
        fun itemClick(position: Int)
    }

    init {
        binding.itemMyClBack.onlyOneClickListener {
            myPageItemClickListener.itemClick(adapterPosition)
        }
    }

    fun bind(item: MyPageItem) {
        binding.itemMyTvTitle.text = item.title
    }

}

class MyPageTopViewHolder(val binding: ItemMyPageMenuTopBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MyPageItem) {
        binding.itemMyTopTvTitle.text = item.title
    }

}