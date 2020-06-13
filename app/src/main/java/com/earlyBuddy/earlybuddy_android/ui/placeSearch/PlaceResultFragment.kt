package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceResultBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding
import com.earlyBuddy.earlybuddy_android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_path.*


class PlaceResultFragment : BaseFragment<FragmentPlaceResultBinding, PlaceSearchViewModel>() {

    private var flag = 0
//    val bundle = Bundle(1)
    private val REQUEST_CODE_START = 7777
    private val REQUEST_CODE_END = 8888

    override val layoutResID: Int
        get() = R.layout.fragment_place_result
    override lateinit var viewModel: PlaceSearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(getViewModelStoreOwner(), ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)
        flag = arguments!!.getInt("flag")

        setRv()
        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            (viewDataBinding.fragPlaceResultRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceResultRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .notifyDataSetChanged()
        })
    }

    fun Fragment.getViewModelStoreOwner(): ViewModelStoreOwner = try {
        requireActivity()
    } catch (e: IllegalStateException) {
        this
    }

    fun setRv(){
        viewDataBinding.fragPlaceResultRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val layoutResID: Int
                        get() = R.layout.item_place_result
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val placeInfo = (item as PlaceSearch)
            val intent = Intent(activity, MapActivity::class.java)
            intent.putExtra("flag", flag)
            intent.putExtra("placeName", placeInfo.placeName)
            intent.putExtra("address", placeInfo.addressName)
            intent.putExtra("roadAddress", placeInfo.roadAddressName)
            intent.putExtra("category", placeInfo.category)
            intent.putExtra("x", placeInfo.x)
            intent.putExtra("y", placeInfo.y)
            startActivityForResult(intent, REQUEST_CODE_START)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_START){
                val intent = Intent()
                intent.putExtra("placeName", data!!.getStringExtra("placeName"))
                intent.putExtra("x", data.getDoubleExtra("x", 0.0))
                intent.putExtra("y", data.getDoubleExtra("y", 0.0))
                activity!!.setResult(Activity.RESULT_OK, intent)
                activity!!.finish()
            } else if (requestCode == REQUEST_CODE_END){
                val intent = Intent()
                intent.putExtra("placeName", data!!.getStringExtra("placeName"))
                intent.putExtra("x", data.getDoubleExtra("x", 0.0))
                intent.putExtra("y", data.getDoubleExtra("y", 0.0))
                activity!!.setResult(Activity.RESULT_OK, intent)
                activity!!.finish()
            }
        }
//        else if(requestCode == REQUEST_CODE_END){
//            if(resultCode == Activity.RESULT_OK){
//                val intent = Intent()
//                Log.e("placeResultFrag endend",data!!.getStringExtra("endPlaceName"))
//                intent.putExtra("endPlaceName", data!!.getStringExtra("endPlaceName"))
//                activity!!.setResult(Activity.RESULT_OK, intent)
//                activity!!.finish()
//            }
//        }
    }
}
