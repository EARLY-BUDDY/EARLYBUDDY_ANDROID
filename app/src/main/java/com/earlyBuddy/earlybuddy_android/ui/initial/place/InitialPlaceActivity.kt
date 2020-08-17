package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.earlyBuddy.earlybuddy_android.InitialObject
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Favorite
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPlaceBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import kotlinx.android.synthetic.main.activity_place.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialPlaceActivity : BaseActivity<ActivityPlaceBinding, InitialPlaceViewModel>(),
    DialogInterface.OnDismissListener {

    companion object {
        val selectedList = arrayOf(false, false, false)
        var favoritePlaceName = ""
        var favoriteCategory = -1
        var favoriteLongitude: Double = 0.0
        var favoriteLatitude: Double = 0.0
        var clickedNum = -1
    }

    init {
        selectedList.fill(false)
    }

    override val layoutResID: Int
        get() = R.layout.activity_place
    override val viewModel: InitialPlaceViewModel by viewModel()
    private val favoriteArr =
        arrayOf(
            Favorite("", -1, 0.0, 0.0),
            Favorite("", -1, 0.0, 0.0),
            Favorite("", -1, 0.0, 0.0)
        )

    private val initialPlaceDialogFragment = InitialPlaceDialogFragment().getInstance()
    private lateinit var cancelList: Array<Cancel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cancelList = arrayOf(
            Cancel(
                viewDataBinding.actInitialPlaceIvFirstCancel,
                viewDataBinding.actInitialPlaceTvFirst,
                viewDataBinding.actInitialPlaceIvFirst
            ),
            Cancel(
                viewDataBinding.actInitialPlaceIvSecondCancel,
                viewDataBinding.actInitialPlaceTvSecond,
                viewDataBinding.actInitialPlaceIvSecond
            ),
            Cancel(
                viewDataBinding.actInitialPlaceIvThirdCancel,
                viewDataBinding.actInitialPlaceTvThird,
                viewDataBinding.actInitialPlaceIvThird
            )
        )

        viewDataBinding.actInitialPlaceClFirst.onlyOneClickListener {
            clickedNum = 1
            initialPlaceDialogFragment.show(supportFragmentManager, "InitialPlace")
        }
        viewDataBinding.actInitialPlaceClSecond.onlyOneClickListener {
            clickedNum = 2
            initialPlaceDialogFragment.show(supportFragmentManager, "InitialPlace")
        }
        viewDataBinding.actInitialPlaceClThird.onlyOneClickListener {
            clickedNum = 3
            initialPlaceDialogFragment.show(supportFragmentManager, "InitialPlace")
        }

        for (i in cancelList.indices) {
            cancelList[i].cancelBtn.onlyOneClickListener {
                cancelList[i].textView.text = "장소를 등록해 주세요."
                cancelList[i].icon.setImageResource(R.drawable.ic_plus)
                favoriteArr[i] = Favorite("", -1, 0.0, 0.0)
                selectedList[i] = false
                isOneSelected()
            }

        }

    }

    private fun isOneSelected() {
        var isAllUnSelected = true
        for (isSelected in selectedList) {
            if (isSelected) {
                viewDataBinding.actInitialPlaceTvRegister.setBackgroundDrawable(
                    resources.getDrawable(
                        R.drawable.bg_25_3092ff
                    )
                )
                isAllUnSelected = false
            }
        }
        if (isAllUnSelected) {
            viewDataBinding.actInitialPlaceTvRegister.setBackgroundDrawable(
                resources.getDrawable(
                    R.drawable.bg_25_c3c3c3
                )
            )
        }

    }

    override fun onDismiss(dialog: DialogInterface?) {
        if (favoritePlaceName != "") {
            Log.e("디스미스 시킴", "$favoritePlaceName,$favoriteLongitude")
            when (clickedNum) {
                1 -> {
                    act_initial_place_iv_first.setImageResource(InitialObject.imageList[favoriteCategory])
                    act_initial_place_tv_first.text = favoritePlaceName
                    favoriteArr[0] = Favorite(
                        favoritePlaceName, favoriteCategory, favoriteLongitude,
                        favoriteLatitude
                    )
                }
                2 -> {
                    act_initial_place_iv_second.setImageResource(InitialObject.imageList[favoriteCategory])
                    act_initial_place_tv_second.text = favoritePlaceName
                    favoriteArr[1] = Favorite(
                        favoritePlaceName, favoriteCategory, favoriteLongitude,
                        favoriteLatitude
                    )
                }
                3 -> {
                    act_initial_place_iv_third.setImageResource(InitialObject.imageList[favoriteCategory])
                    act_initial_place_tv_third.text = favoritePlaceName
                    favoriteArr[2] = Favorite(
                        favoritePlaceName, favoriteCategory, favoriteLongitude,
                        favoriteLatitude
                    )
                }
                else -> {

                }
            }
            selectedList[clickedNum - 1] = true

        }

        favoritePlaceName = ""
        clickedNum = -1
        isOneSelected()
    }

    inner class Cancel(
        val cancelBtn: ImageView, val textView: TextView, val icon: ImageView
    )

}
