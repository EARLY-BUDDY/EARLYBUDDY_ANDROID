package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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
import com.earlyBuddy.earlybuddy_android.databinding.FragmentPlaceListBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPlaceListBinding
import kotlinx.android.synthetic.main.activity_end_place_search.*
import kotlinx.android.synthetic.main.activity_start_place_search.*


class PlaceListFragment : BaseFragment<FragmentPlaceListBinding, PlaceSearchViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_place_list
    override lateinit var viewModel: PlaceSearchViewModel

    val placeResultFragment  = PlaceResultFragment()
    private var flag = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(getViewModelStoreOwner(), ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)
        flag = arguments!!.getInt("flag")
        setRv()

//        viewModel.placeList.observe(viewLifecycleOwner, Observer {
//            Log.e("검색 리스트 observe", "실행실행~~₩")
//            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
//                .replaceAll(it)
//            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
//                .notifyDataSetChanged()
//        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun Fragment.getViewModelStoreOwner(): ViewModelStoreOwner = try {
        requireActivity()
    } catch (e: IllegalStateException) {
        this
    }

    fun setRv(){
        Log.e("검색 리스트 setRv", "실행실행~~₩")
        viewDataBinding.fragPlaceListRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.placeRes
                    override val layoutResID: Int
                        get() = R.layout.item_place_list
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            Log.e("검색 리스트 observe", "실행실행~~₩")
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .replaceAll(it)
            (viewDataBinding.fragPlaceListRv.adapter as BaseRecyclerViewAdapter<PlaceSearch, ItemPlaceListBinding>)
                .notifyDataSetChanged()
        })
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val placeName = (item as PlaceSearch).placeName
            viewModel.getPlaceSearchData(placeName)
            val keyboard: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(requireView().windowToken, 0)

            if(flag==1){
                requireActivity().currentFocus?.clearFocus()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_start_place_search_container,
                        placeResultFragment
                    ).commit()
            }
            else if(flag==2){
                requireActivity().currentFocus?.clearFocus()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_end_place_search_container,
                        placeResultFragment
                    ).commit()
            }
        }
    }
}
