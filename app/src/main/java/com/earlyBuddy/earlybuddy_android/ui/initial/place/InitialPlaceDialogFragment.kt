package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.databinding.DialogFragmentPlaceFavoriteIconSelectBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.StartPlaceSearchActivity


class InitialPlaceDialogFragment : DialogFragment() {


    private lateinit var binding: DialogFragmentPlaceFavoriteIconSelectBinding
    private lateinit var imageList: Array<InitialImage>
    private lateinit var clickIconList: Array<ImageView>

    fun getInstance(): InitialPlaceDialogFragment {
        return InitialPlaceDialogFragment()
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
        clickIconList = arrayOf(
            binding.dialogFragmentPlaceFavoriteIvHome,
            binding.dialogFragmentPlaceFavoriteIvOffice,
            binding.dialogFragmentPlaceFavoriteIvSchool
        )

        imageList = arrayOf(
            InitialImage(
                binding.dialogFragmentPlaceFavoriteIvHome,
                R.drawable.ic_home_selected
            ),
            InitialImage(
                binding.dialogFragmentPlaceFavoriteIvOffice,
                R.drawable.ic_office_selected
            ),
            InitialImage(
                binding.dialogFragmentPlaceFavoriteIvSchool,
                R.drawable.ic_school_selected
            )
        )

        setBackground()
//        setClickableImage()

        binding.dialogFragmentPlaceFavoriteIvCancel.onlyOneClickListener {
            dismiss()
        }

        for (i in clickIconList.indices) {
            clickIconList[i].onlyOneClickListener {
                val intent = Intent(activity, StartPlaceSearchActivity::class.java)
                intent.putExtra("initial", 1)
                intent.putExtra("favoriteCategory", i)   // 집은 0
                startActivityForResult(intent, 111)
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 111) {
                Log.e("XXX", data!!.getStringExtra("placeName")).toString()
                InitialPlaceActivity.favoritePlaceName = data!!.getStringExtra("placeName")
                InitialPlaceActivity.favoriteCategory = data!!.getIntExtra("favoriteCategory", -1)
                InitialPlaceActivity.favoriteLongitude = data!!.getDoubleExtra("x", 0.0)
                InitialPlaceActivity.favoriteLatitude = data!!.getDoubleExtra("y", 0.0)
                dismiss()
            }
        }
    }

    private fun setBackground() {
        // 배경에 희게 각지게 나오는 거 방지.
        with(dialog!!.window!!) {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setClickableImage() {
        for (i in InitialPlaceActivity.selectedList.indices) {
            if (InitialPlaceActivity.selectedList[i]) {
                imageList[i].imageView.isClickable = false
                imageList[i].imageView.setImageResource(imageList[i].drawable)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog!!)
        val activity: Activity? = activity
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

}

class InitialImage(
    val imageView: ImageView,
    val drawable: Int
)