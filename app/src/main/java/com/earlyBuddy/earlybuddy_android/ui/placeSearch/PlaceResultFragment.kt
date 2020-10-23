package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceResultBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceResultBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PlaceResultFragment : BaseFragment<FragmentPlaceResultBinding, PlaceSearchViewModel>() {

    private var flag = 0
//    val bundle = Bundle(1)
    private val REQUEST_CODE_START = 7777
    private val REQUEST_CODE_END = 8888

    override val layoutResID: Int
        get() = R.layout.fragment_place_result
    override val viewModel: PlaceSearchViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flag = arguments!!.getInt("flag")
        setRv()
    }

    fun setRv(){
        viewDataBinding.fragPlaceResultRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceResultBinding>() {
                    override val layoutResID: Int
                        get() = R.layout.item_place_result
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            (viewDataBinding.fragPlaceResultRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceResultBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceResultRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceResultBinding>)
                .notifyDataSetChanged()
        })
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val placeInfo = (item as PlaceSearch)
            val intent = Intent(activity, MapActivity::class.java)
            if (StartPlaceSearchActivity.getByInitial == 1) {
                intent.putExtra("initial", 1)
                intent.putExtra("favoriteCategory", StartPlaceSearchActivity.favoriteCategory)
            }
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
                intent.putExtra("flag", data.getIntExtra("flag", 0))
                intent.putExtra("favoriteCategory", data.getIntExtra("favoriteCategory", -1))
                activity!!.setResult(Activity.RESULT_OK, intent)
                activity!!.finish()
            } else if (requestCode == REQUEST_CODE_END){
                val intent = Intent()
                intent.putExtra("placeName", data!!.getStringExtra("placeName"))
                intent.putExtra("x", data.getDoubleExtra("x", 0.0))
                intent.putExtra("y", data.getDoubleExtra("y", 0.0))
                intent.putExtra("flag", data.getIntExtra("flag", 0))
                Log.e("flag1", data.getIntExtra("flag", 0).toString())
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
