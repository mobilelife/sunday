package com.ragaisis.sunday.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ragaisis.sunday.R
import com.ragaisis.sunday.entities.AddressDetailsHomes

class SuggestionAdapter(val context: Context, items: List<AddressDetailsHomes>) : RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

    var items: List<AddressDetailsHomes>? = items
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(inflater.inflate(R.layout.row_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.rowHomeTypeTextView.text = items?.get(position)?.home?.homeType.toString()
        holder.rowAreaHomeTextView.text = String.format(
                context.getString(R.string.home_area), items?.get(position)?.home?.areaHome.toString()
        )
        holder.rowLocationNameTextView.text = items?.get(position)?.home?.locationName.toString()
        holder.rowCityTextView.text = items?.get(position)?.home?.city.toString()
        val price = items?.get(position)?.home?.price
        if ((price != null)) {
            holder.rowPriceTextView.text = String.format(
                    context.getString(R.string.price_kr),
                    (price / 100).toString()
            )
        }
        hideIfNoData(holder)
    }

    private fun hideIfNoData(holder: SuggestionViewHolder) {
        holder.rowHomeTypeTextView.visibility = if (holder.rowHomeTypeTextView.text.isNotEmpty()) VISIBLE else GONE
        holder.rowAreaHomeTextView.visibility = if (holder.rowAreaHomeTextView.text.isNotEmpty()) VISIBLE else GONE
        holder.rowLocationNameTextView.visibility = if (holder.rowLocationNameTextView.text.isNotEmpty()) VISIBLE else GONE
        holder.rowCityTextView.visibility = if (holder.rowCityTextView.text.isNotEmpty()) VISIBLE else GONE
        holder.rowPriceTextView.visibility = if (holder.rowPriceTextView.text.isNotEmpty()) VISIBLE else GONE
    }

    class SuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView
        val rowHomeTypeTextView: TextView
        val rowAreaHomeTextView: TextView
        val rowLocationNameTextView: TextView
        val rowCityTextView: TextView
        val rowPriceTextView: TextView

        init {
            imageView = view.findViewById(R.id.rowImageView)
            rowHomeTypeTextView = view.findViewById(R.id.rowHomeTypeTextView)
            rowAreaHomeTextView = view.findViewById(R.id.rowAreaHomeTextView)
            rowLocationNameTextView = view.findViewById(R.id.rowLocationNameTextView)
            rowCityTextView = view.findViewById(R.id.rowCityTextView)
            rowPriceTextView = view.findViewById(R.id.rowPriceTextView)
        }

    }
}