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

class  PreferListDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_frag_prefer_list, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tag = arguments!!.getInt("preferIdx")
        Log.e("peferIdx", tag.toString())

        when (tag) {
            0 -> {
                dial_frag_prefer_list_tv_all.setTextColor(resources.getColor(R.color.main_color))
                dial_frag_prefer_iv_all.visibility = View.VISIBLE
            }
            2 -> {
                dial_frag_prefer_list_tv_bus.setTextColor(resources.getColor(R.color.main_color))
                dial_frag_prefer_iv_bus.visibility = View.VISIBLE
            }
            else -> {
                dial_frag_prefer_list_tv_sub.setTextColor(resources.getColor(R.color.main_color))
                dial_frag_prefer_iv_sub.visibility = View.VISIBLE
            }
        }

        dial_frag_prefer_list_ll_all.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("preferIdx", 0)
            Log.e("prefer bundle", bundle.toString())
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
        dial_frag_prefer_list_ll_bus.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("preferIdx", 2)
            Log.e("prefer bundle", bundle.toString())
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
        dial_frag_prefer_list_ll_sub.onlyOneClickListener {
            val bundle = Bundle()
            bundle.putInt("preferIdx", 1)
            Log.e("prefer bundle", bundle.toString())
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }
    }
}