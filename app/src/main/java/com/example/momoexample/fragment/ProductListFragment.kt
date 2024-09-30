package com.example.momoexample.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.momoexample.adapter.ProductAdapter
import com.example.momoexample.adapter.PromoAdapter
import com.example.momoexample.databinding.ProductListFragmentBinding
import com.example.momoexample.viewmodel.PageViewModel


class ProductListFragment : Fragment() {

    private lateinit var bindingView: ProductListFragmentBinding
    private lateinit var pageViewModel: PageViewModel

    private val tagName = "percent"
    private val displayOneColumn = 1
    private val displayTwoColumn = 2
    private val displayThreeColumn = 3
    //todo scroll to top function
   // private var lastProductItemPosition = 0
    private var totalProductNum = 0

    private var productSpanCount = displayTwoColumn

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView = ProductListFragmentBinding.inflate(inflater, container, false)
        pageViewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        return bindingView.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageViewModel.getPageData()
        observeData()
    }

    private fun observeData() {
        pageViewModel.getDataFailed.observe(viewLifecycleOwner) { isApiFailed ->
            bindingView.loadingProgress.visibility = View.GONE
            bindingView.errorTv.visibility = if (isApiFailed) View.VISIBLE else View.GONE
            bindingView.pageInfo.visibility = if (isApiFailed) View.GONE else View.VISIBLE
        }
        pageViewModel.pageLiveData.observe(viewLifecycleOwner) { pageModel ->
            if (pageModel.promoteList.isNotEmpty()) {
                setPromoRecycleView()
            }
            if (pageModel.productList.isNotEmpty()) {
                totalProductNum = pageModel.productList.size
                setFilterView()
                setProductRecycleView()
            }
        }
    }

    private fun setPromoRecycleView() {
        bindingView.promoList.layoutManager = GridLayoutManager(this.context, displayThreeColumn)
        pageViewModel.pageLiveData.value?.promoteList?.let {
            val promoAdapter = PromoAdapter(it)
            bindingView.promoList.adapter = promoAdapter
        }
    }

    private fun setFilterView() {
        bindingView.filterArea.visibility = View.VISIBLE
        bindingView.filterArea.cardBackgroundColor
        val colorInt =
            context?.getColor(com.google.android.material.R.color.material_dynamic_neutral80)
        colorInt?.let { bindingView.filterArea.setBackgroundColor(it) }
        bindingView.filterClickArea.setOnClickListener {
            productSpanCount =
                if (productSpanCount == displayTwoColumn) displayOneColumn else displayTwoColumn
            bindingView.productList.layoutManager =
                GridLayoutManager(this.context, productSpanCount)
        }
    }

    private fun setProductRecycleView() {
        bindingView.productList.setLayoutManager(object :
            GridLayoutManager(this.context, productSpanCount) {
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)

                printAllViewHolderVisiblePercent()

            }
        })
        pageViewModel.pageLiveData.value?.productList?.let {
            val promoAdapter = ProductAdapter(
                onClick = { position ->
                    pageViewModel.setIsLikeStatus(position)
                },
                it
            )
            bindingView.productList.adapter = promoAdapter
            detectProductRecycleViewScroll()
        }
    }

    private fun detectProductRecycleViewScroll() {
        bindingView.productList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //TODO scroll to top function
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (getPromoListHeightOnScreen() > 0  && bindingView.toTopInfo.isVisible) {
//                    bindingView.toTopInfo.visibility = View.GONE
//                }
//                if (getPromoListHeightOnScreen() <= 0) {
//                    (recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
//                        val lastItemPosition = layoutManager.findLastVisibleItemPosition()
//
//                        if (lastProductItemPosition != lastItemPosition) {
//                            lastProductItemPosition = layoutManager.findLastVisibleItemPosition()
//                            val remainProductNum = totalProductNum - (lastProductItemPosition + 1)
//                            Log.d("Alex", "remainProductNum:$remainProductNum")
//                            bindingView.errorTv.text = getString(R.string.product_remain, remainProductNum)
//                        }
//                    }
//                    if (!bindingView.toTopInfo.isVisible) {
//                        bindingView.toTopInfo.visibility = View.VISIBLE
//                    }
//                }
//            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    printAllViewHolderVisiblePercent()
                }
            }
        })
    }

    private fun printAllViewHolderVisiblePercent() {
        bindingView.productList.adapter?.itemCount?.let {
            val promoListHeight = getPromoListHeightOnScreen()
            val globalVisibilityRectangle = Rect()
            val productListHeight = bindingView.productList.measuredHeight - promoListHeight
            for (i in 0..<it) {
                var visiblePercent = 0
                bindingView.productList.findViewHolderForAdapterPosition(i)?.let { view ->
                    if (view.itemView.y < productListHeight) {
                        val originViewHeight = view.itemView.measuredHeight
                        view.itemView.getGlobalVisibleRect(globalVisibilityRectangle)
                        val visibleHeight = globalVisibilityRectangle.bottom - globalVisibilityRectangle.top
                        visiblePercent = ((visibleHeight.toDouble() / originViewHeight) * 100).toInt()
                    }
                }
                Log.d(tagName, "index$i $visiblePercent%")
            }
        }
    }

    private fun getPromoListHeightOnScreen(): Int {
        val promoGlobalRect = Rect()
        bindingView.promoList.getGlobalVisibleRect(promoGlobalRect)
        val topPosition = promoGlobalRect.top
        val bottomPosition = promoGlobalRect.bottom
        return if (bottomPosition <= 0) 0 else bottomPosition - topPosition
    }
}