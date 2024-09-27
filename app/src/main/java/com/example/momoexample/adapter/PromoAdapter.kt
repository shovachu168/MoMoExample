package com.example.momoexample.adapter

import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Horizontal
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PromoAdapter(private val list: List<String>) :
    ListAdapter<String, PromoAdapter.PromoViewHolder>(PromoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        return PromoViewHolder(ComposeView(parent.context), list)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.count()
    }

    class PromoViewHolder(private val composeView: ComposeView, private val list: List<String>) :
        RecyclerView.ViewHolder(composeView) {
        private lateinit var textViewAlign: Alignment.Horizontal
        fun bind(position: Int) {
            val title = list[position]
            setTextAlign(position)
            composeView.setContent {
                PromoItem(title, textViewAlign)
            }
        }

        private fun setTextAlign(position: Int) {
            val positionMod = position % 3
            textViewAlign = when (positionMod) {
                0 -> Alignment.Start
                1 -> Alignment.CenterHorizontally
                else -> Alignment.End
            }
        }
    }
}

@Composable
fun PromoItem(title: String, align: Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .height(60.dp), verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier.align(align)
        )
    }
}

class PromoDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}