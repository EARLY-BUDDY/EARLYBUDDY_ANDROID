package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.databinding.DialogFragmentPlaceFavoriteIconSelectBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener

class PlaceDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentPlaceFavoriteIconSelectBinding

    fun getInstance(): PlaceDialogFragment {
        return PlaceDialogFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentPlaceFavoriteIconSelectBinding.inflate(
            LayoutInflater.from(this.context),
            container,
            false
        )

        setBackground()

        binding.dialogFragmentPlaceFavoriteIvCancel.onlyOneClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun setBackground() {
        // 배경에 희게 각지게 나오는 거 방지.
        with(dialog!!.window!!) {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}