package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.databinding.DialogFragmentScheduleRegistBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity

class ScheduleDialogFragment(
    val scheduleIdx : Int
) : DialogFragment() {

    lateinit var databinding : DialogFragmentScheduleRegistBinding

    lateinit var listener : OnDialogDismissedListener

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_schedule_regist, null, false)
        return databinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.dialogFragmentSchePopUpTvCheck.onlyOneClickListener {
            var intent = Intent(this@ScheduleDialogFragment.context, ScheduleDetailActivity::class.java)
            intent.putExtra("scheduleIdx", scheduleIdx)
            dismiss()
            startActivity(intent)
        }
        databinding.dialogFragmentSchePopUpTvHome.onlyOneClickListener {
            val intent = Intent(this@ScheduleDialogFragment.context, HomeActivity::class.java)
            ActivityCompat.finishAffinity(this@ScheduleDialogFragment.activity as Activity)
            dismiss()
            startActivity(intent)
        }
    }
}