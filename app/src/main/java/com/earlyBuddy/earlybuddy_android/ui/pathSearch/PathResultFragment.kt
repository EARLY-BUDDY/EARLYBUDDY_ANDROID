package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

}
