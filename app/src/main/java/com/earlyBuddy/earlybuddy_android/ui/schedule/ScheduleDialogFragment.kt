package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.databinding.DialogFragmentScheduleRegistBinding

class ScheduleDialogFragment(
    val scheduleIdx : Int
) : DialogFragment() {

    lateinit var databinding : DialogFragmentScheduleRegistBinding

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
        databinding.dialogFragmentSchePopUpTvCheck.setOnClickListener {
//            var goToCheck = Intent(this@ScheduleDialogFragment.context, ScheduleCompleteActivity::class.java)
//            goToCheck.putExtra("scheduleIdx", scheduleIdx)
//            dismiss()
//            startActivity(goToCheck)
        }
        databinding.dialogFragmentSchePopUpTvHome.setOnClickListener {
            dismiss()
        }
    }
}