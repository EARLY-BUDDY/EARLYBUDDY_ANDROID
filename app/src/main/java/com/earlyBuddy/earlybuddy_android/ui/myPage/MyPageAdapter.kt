package com.earlyBuddy.earlybuddy_android.ui.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.model.MyPageItem
import com.earlyBuddy.earlybuddy_android.databinding.ItemMyPageMenuBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemMyPageMenuTopBinding

class MyPageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val myPageItemData = listOf<MyPageItem>(
        MyPageItem("전체 알림", null),
        MyPageItem("계정 관리", null),
        MyPageItem("자주 가는 장소", null),
        MyPageItem("오픈소스 라이선스", null),
        MyPageItem("이용약관", null),
        MyPageItem("개인정보처리방침", null)
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
            1 -> {  // 첫번째 토글 알림 설정 창
                val topItemBinding =
                    ItemMyPageMenuTopBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MyPageTopViewHolder(topItemBinding)
            }
            else -> {
                val generalItemBinding =
                    ItemMyPageMenuBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MyPageViewHolder(generalItemBinding)

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

class MyPageViewHolder(val binding: ItemMyPageMenuBinding) : RecyclerView.ViewHolder(binding.root) {

    interface goToAnyClickListener{
        fun go
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