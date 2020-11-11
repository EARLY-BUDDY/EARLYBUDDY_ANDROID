package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_frag_prefer_list.*
import kotlinx.android.synthetic.main.dialog_frag_sort_list.*

class SortListDialogFragment : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_frag_sort_list, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tag = arguments!!.getInt("sortIdx")
        Log.e("sortIdx", tag.toString())

        if(tag==0){
            dial_frag_sort_list_tv_best.setTextColor(resources.getColor(R.color.main_color))
            dial_frag_sort_iv_best.visibility = View.VISIBLE
        }else if(tag==1){
            dial_frag_sort_list_tv_time.setTextColor(resources.getColor(R.color.main_color))
            dial_frag_sort_iv_time.visibility = View.VISIBLE
        }else if(tag==2){
            dial_frag_sort_list_tv_trans.setTextColor(resources.getColor(R.color.main_color))
            dial_frag_sort_iv_trans.visibility = View.VISIBLE
        }else{
            dial_frag_sort_list_tv_walk.setTextColor(resources.getColor(R.color.main_color))
            dial_frag_sort_iv_walk.visibility = View.VISIBLE
        }

        dial_frag_sort_list_ll_best.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("sortIdx", 0)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
        dial_frag_sort_list_ll_time.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("sortIdx", 1)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()

        }
        dial_frag_sort_list_ll_trans.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("sortIdx", 2)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
        dial_frag_sort_list_ll_walk.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("sortIdx", 3)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
    }
}