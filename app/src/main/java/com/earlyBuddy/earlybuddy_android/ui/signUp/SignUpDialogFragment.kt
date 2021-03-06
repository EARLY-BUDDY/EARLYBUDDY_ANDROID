package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication

import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseDialogFragment
import com.earlyBuddy.earlybuddy_android.databinding.DialogFragmentSignUpCompleteBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_fragment_sign_up_complete.*

class SignUpDialogFragment : DialogFragment() {

    lateinit var listener : OnDialogDismissedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.dialog_fragment_sign_up_complete, container, false)
        dialog_fragment_sign_up_tv_login.onlyOneClickListener {
            dismiss()
        }
        return view
    }

    fun setOnDialogDismissedListener(listener: OnDialogDismissedListener) {
        this.listener = listener
    }
    interface OnDialogDismissedListener {
        fun onDialogDismissed()
    }
    override fun dismiss() {
        listener.onDialogDismissed()
        super.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
