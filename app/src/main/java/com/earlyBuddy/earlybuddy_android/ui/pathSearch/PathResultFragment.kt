package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPathResultBinding
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.TestPathActivity
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.io.Serializable

class PathResultFragment : BaseFragment<FragmentPathResultBinding, PathViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_path_result
    override val viewModel: PathViewModel by sharedViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRv()
        setNotiSpinner()
//        setSpinner()
    }

    private fun setRv(){
        val pathItemAdapter = PathItemAdapter(
            object : PathItemViewHolder.OnClickPathItemListener {
                override fun onClickPathItem(position: Int, pathIdx: Int) {
                    val intent = Intent(requireContext(), TestPathActivity::class.java)
                    intent.putExtra("path", viewModel.routeList.value!![position])
                    intent.putExtra("startAdd", arguments!!.getString("startAdd"))
                    intent.putExtra("endAdd", arguments!!.getString("endAdd"))
                    startActivity(intent)
                }
            }
        )
        viewDataBinding.fragPathResultRv.apply {
            adapter = pathItemAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewModel.routeList.observe(viewLifecycleOwner, Observer {
            pathItemAdapter.run{
                replaceAll(it)
                notifyDataSetChanged()
            }
        })
    }

    fun setNotiSpinner(){
//        val notiSpinner: Spinner = findViewById(R.id.act_schedule_sp_noti)
        ArrayAdapter.createFromResource(requireContext(), R.array.prefer_path, R.layout.item_spinner_list)
            .also {
                    adapter -> adapter.setDropDownViewResource(R.layout.item_spinner_list)
                viewDataBinding.fragPathResultSpPrefer.adapter = adapter
            }
        viewDataBinding.fragPathResultSpPrefer.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Log.e("알림배차몇분?!!!!!?!?!?!??!", position.toString())
                var userNoti = viewDataBinding.fragPathResultSpPrefer.selectedItemPosition
                when(userNoti){
//                    0 -> arriveCount = 1
//                    1 -> arriveCount = 2
//                    2 -> arriveCount = 3
//                    3 -> arriveCount = 0
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun setSpinner(){
        val array = resources.getStringArray(R.array.prefer_path)
        val adapter : ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.item_spinner_list, array)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        viewDataBinding.fragPathResultSpPrefer.adapter = adapter

//        viewDataBinding.fragPathResultSpPrefer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
//                when(position) {
//                    0   ->  {
//                    }
//                    1   ->  {
//
//                    }
//                    //...
//                    else -> {
//
//                    }
//                }
//            }
//        }
    }

}
