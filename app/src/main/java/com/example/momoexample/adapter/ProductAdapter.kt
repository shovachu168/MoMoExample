package com.example.momoexample.adapter

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.momoexample.R
import com.example.momoexample.datamodel.ProductModel

class ProductAdapter(
    private val onClick: (Int) -> Unit,
    private val list: List<ProductModel>) :
    ListAdapter<ProductModel, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ComposeView(parent.context), list, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.count()
    }

    class ProductViewHolder(private val composeView: ComposeView, private val modelList: List<ProductModel>, private val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(composeView) {
        fun bind(position: Int) {
            val model = modelList[position]
            composeView.setContent {
                ProductItem(model, position, onClick)
            }
        }
    }
}

@Composable
fun ProductItem(model: ProductModel, position: Int, onClick: (Int) -> Unit) {
    var isLike by remember { mutableStateOf(model.isLike) }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .height(90.dp)
            .fillMaxWidth()
    )
    {
        Text(
            text = model.name,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        Image(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .padding(bottom = 18.dp, end = 18.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    isLike = !isLike
                    onClick(position)
                },
            painter = painterResource(id = if (isLike) R.drawable.is_good_job else R.drawable.not_good_job),
            contentDescription = ""
        )
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id && oldItem.isLike == newItem.isLike
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }
}