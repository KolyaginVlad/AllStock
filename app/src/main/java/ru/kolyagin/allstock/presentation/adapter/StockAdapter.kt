package ru.kolyagin.allstock.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kolyagin.allstock.R
import ru.kolyagin.allstock.databinding.StockItemBinding
import ru.kolyagin.allstock.presentation.model.SymbolInfo

class StockAdapter : PagingDataAdapter<SymbolInfo, StockAdapter.StockViewHolder>(diffCallback) {

    private var layoutInflater: LayoutInflater? = null

    fun updateItem(symbol: String, price: Double) {
        snapshot().items.find { it.symbol == symbol }?.let {
            val index = snapshot().items.indexOf(it)
            peek(index)?.price = price
            notifyItemChanged(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = layoutInflater ?: run {
            LayoutInflater.from(parent.rootView.context).also { layoutInflater = it }
        }
        return StockViewHolder(
            StockItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class StockViewHolder(
        private val binding: StockItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: SymbolInfo) {
            binding.apply {
                description.text = data.description
                symbol.text = data.displaySymbol
                if (data.price != null) {
                    loadIndicator.visibility = View.INVISIBLE
                    price.visibility = View.VISIBLE
                    if (data.price == 0.0){
                        price.text = root.context.getString(R.string.no_data)
                    }
                    else {
                        price.text = "${data.price.toString()}  $"
                    }
                } else {
                    loadIndicator.visibility = View.VISIBLE
                    price.visibility = View.INVISIBLE
                }
            }
        }

    }

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<SymbolInfo> =
            object : DiffUtil.ItemCallback<SymbolInfo>() {
                override fun areItemsTheSame(oldItem: SymbolInfo, newItem: SymbolInfo): Boolean =
                    oldItem.symbol == newItem.symbol

                override fun areContentsTheSame(oldItem: SymbolInfo, newItem: SymbolInfo): Boolean =
                    oldItem == newItem
            }
    }
}