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
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.TestPathActivity
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.io.Serializable

class PathResultFragment : BaseFragment<FragmentPathResultBinding, PathViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_path_result
    override val viewModel: PathViewModel by sharedViewModel()
    val PREFER_LIST_DIALOG = 1
    val SORT_LIST_DIALOG = 2
    var preferIdx = 0
    var sortIdx = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRv()
        setClick()
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

    fun setClick(){
        viewDataBinding.fragPathResultLlPrefer.onlyOneClickListener {
            val preferListDialog = PreferListDialogFragment()
            val args = Bundle()
            args.putInt("preferIdx", preferIdx)
            preferListDialog.arguments = args
            preferListDialog.setTargetFragment(this, PREFER_LIST_DIALOG)
            preferListDialog.show(requireActivity().supportFragmentManager, "dialog")
        }

        viewDataBinding.fragPathResultLlSort.onlyOneClickListener {
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
        val bundle = data!!.extras
        if(resultCode == PREFER_LIST_DIALOG) {
            preferIdx = bundle!!.getInt("preferIdx")
        }else if(resultCode == SORT_LIST_DIALOG) {
            sortIdx = bundle!!.getInt("sortIdx")
        }else{
            Log.e("onactResult", "fail")
        }
    }
}
