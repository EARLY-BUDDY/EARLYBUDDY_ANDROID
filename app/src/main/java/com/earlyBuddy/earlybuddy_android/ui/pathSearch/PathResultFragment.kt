package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPathResultBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.VerticalPathActivity
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PathResultFragment : BaseFragment<FragmentPathResultBinding, PathViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_path_result
    override val viewModel: PathViewModel by sharedViewModel()
    private val PREFER_LIST_DIALOG = 1
    private val SORT_LIST_DIALOG = 2
    private val OPEN_VERTIVAL_PATH = 7777
    var preferIdx = 0
    var sortIdx = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.e("scheDate", arguments!!.getString("scheDate") + " " + arguments!!.getString("scheTime"))
        viewDataBinding.fragPathReultTvDate.text = arguments!!.getString("scheDate")
        viewDataBinding.fragPathResultTvTime.text = arguments!!.getString("scheTime")

        setRv()
        setClick()
    }

    private fun setRv(){
        val pathItemAdapter = PathItemAdapter(
            object : PathItemViewHolder.OnClickPathItemListener {
                override fun onClickPathItem(position: Int, pathIdx: Int) {
                    val intent = Intent(requireContext(), VerticalPathActivity::class.java)
                    intent.putExtra("path", viewModel.routeList.value!![position])
                    intent.putExtra("startAdd", arguments!!.getString("startAdd"))
                    intent.putExtra("endAdd", arguments!!.getString("endAdd"))
                    intent.putExtra("scheTime", arguments!!.getString("scheTime"))
                    startActivityForResult(intent, OPEN_VERTIVAL_PATH)
                }
            }
        )
        pathItemAdapter.setHasStableIds(true)
        val animator: RecyclerView.ItemAnimator? = viewDataBinding.fragPathResultRv.itemAnimator
        if (animator is SimpleItemAnimator) animator.supportsChangeAnimations = false

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

    fun setClick(){
        viewDataBinding.fragPathResultRlPrefer.onlyOneClickListener {
            val preferListDialog = PreferListDialogFragment()
            val args = Bundle()
            args.putInt("preferIdx", preferIdx)
            preferListDialog.arguments = args
            preferListDialog.setTargetFragment(this, PREFER_LIST_DIALOG)
            preferListDialog.show(requireActivity().supportFragmentManager, "dialog")
        }

        viewDataBinding.fragPathResultRlSort.onlyOneClickListener {
            val sortListDialog = SortListDialogFragment()
            val args = Bundle()
            args.putInt("sortIdx", sortIdx)
            sortListDialog.arguments = args
            sortListDialog.setTargetFragment(this, SORT_LIST_DIALOG)
            sortListDialog.show(requireActivity().supportFragmentManager, "dialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = data?.extras

        if(resultCode == PREFER_LIST_DIALOG) {
            if (resultCode == Activity.RESULT_OK) {
                preferIdx = bundle!!.getInt("preferIdx")
                (activity as PathActivity).searchPathType = preferIdx
                (activity as PathActivity).getRoute()

                if (preferIdx == 0) {
                    viewDataBinding.fragPathResultTvPrefer.text = "선호수단"
                } else if (preferIdx == 1) {
                    viewDataBinding.fragPathResultTvPrefer.text = "지하철"
                } else {
                    viewDataBinding.fragPathResultTvPrefer.text = "버스"
                }
            }
            else{
                Log.e("onActResult", "fail")
            }
        }else if(resultCode == SORT_LIST_DIALOG) {
            if (resultCode == Activity.RESULT_OK) {
                sortIdx = bundle!!.getInt("sortIdx")

                when (sortIdx) {
                    0 -> {
                        viewDataBinding.fragPathResultTvSort.text = "최적경로순"
                        (activity as PathActivity).sortPathType = 0
                        (activity as PathActivity).getRoute()
                    }
                    1 -> {
                        viewDataBinding.fragPathResultTvSort.text = "최단시간순"
                        (activity as PathActivity).sortPathType = 1
                        (activity as PathActivity).sortRoute()
                    }
                    2 -> {
                        viewDataBinding.fragPathResultTvSort.text = "최소환승순"
                        (activity as PathActivity).sortPathType = 2
                        (activity as PathActivity).sortRoute()
                    }
                    3 -> {
                        viewDataBinding.fragPathResultTvSort.text = "최소도보순"
                        (activity as PathActivity).sortPathType = 3
                        (activity as PathActivity).sortRoute()
                    }
                }
            }else{
                Log.e("onActResult", "fail")
            }
        } else if (requestCode == OPEN_VERTIVAL_PATH) {
            if (resultCode == Activity.RESULT_OK) {
                val pathData = data!!.getSerializableExtra("path") as Path
                val startAdd = data.getStringExtra("startAdd")
                val endAdd = data.getStringExtra("endAdd")
                val intent = Intent()
                intent.putExtra("path", pathData)
                intent.putExtra("startAdd", startAdd)
                intent.putExtra("endAdd", endAdd)
                requireActivity().setResult(Activity.RESULT_OK, intent)
                requireActivity().finish()
            }else{
                Log.e("onActResult", "fail")
            }
        } else{
            Log.e("onActResult", "fail")
        }
    }
}
