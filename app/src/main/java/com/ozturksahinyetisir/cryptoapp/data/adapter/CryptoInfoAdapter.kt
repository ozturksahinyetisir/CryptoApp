package com.ozturksahinyetisir.cryptoapp.data.adapter
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.databinding.ItemCryptoInfoBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil


class CryptoInfoAdapter :
    ListAdapter<CryptoInfo, CryptoInfoAdapter.CryptoInfoViewHolder>(CryptoInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoInfoBinding.inflate(inflater, parent, false)
        return CryptoInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoInfoViewHolder, position: Int) {
        val cryptoInfo = getItem(position)
        holder.bind(cryptoInfo)
    }

    inner class CryptoInfoViewHolder(private val binding: ItemCryptoInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoInfo: CryptoInfo) {
            val price = cryptoInfo.quote.USD.price.toString().toFloatOrNull()
            val percent = cryptoInfo.quote.USD.percent_change_24h.toString().toFloatOrNull()
            val rank = cryptoInfo.cmc_rank

            if (price == null || price < 0) {
                binding.cryptoPriceTv.text = "Invalid Price"
                return
            }
            val formattedPercent = String.format("%.3f", percent)

            val percentColor = if (percent ?: 0f >= 0) android.R.color.holo_green_dark else android.R.color.holo_red_dark
            val formattedPercentSpannable = SpannableString("($formattedPercent)")
            formattedPercentSpannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(binding.root.context, percentColor)),
                0,
                formattedPercent.length+2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val formattedPrice = when {
                price == 0f -> String.format("%.5f", price)
                price == 0.1f -> String.format("%.6f", price)
                price == 0.01f -> String.format("%.7f", price)
                price < 0.001f -> String.format("%.9f",price)
                else -> String.format("%.3f", price)
            }

            binding.rankTv.text = "#${cryptoInfo.cmc_rank}"
            binding.cryptoNameTextView.text = cryptoInfo.name
            binding.symbolTv.text = cryptoInfo.symbol
            binding.percentTv.text = formattedPercentSpannable
            binding.cryptoPriceTv.text = formattedPrice
        }
    }

class CryptoInfoDiffCallback : DiffUtil.ItemCallback<CryptoInfo>() {
    override fun areItemsTheSame(oldItem: CryptoInfo, newItem: CryptoInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CryptoInfo, newItem: CryptoInfo): Boolean {
        return oldItem == newItem
    }
}}