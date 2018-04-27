package com.ragaisis.sunday.cursors

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import com.ragaisis.sunday.R

class SuggestionCursorAdapter(context: Context?, c: Cursor?, autoRequery: Boolean) : CursorAdapter(context, c, autoRequery) {

    companion object {
        const val COLUMN_TYPE = "TYPE"
        const val COLUMN_TYPE_HEADER = "HEADER"
    }

    private var inflater: LayoutInflater

    init {
        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? {
        val holder = ViewHolder()
        val v: View?
        if (cursor?.getString(cursor.getColumnIndex(COLUMN_TYPE)).equals(COLUMN_TYPE_HEADER)) {
            v = inflater.inflate(R.layout.view_search_suggestion_header, parent, false)
            holder.textView = v.findViewById(R.id.view_search_suggestion_header_text_view) as TextView
        } else {
            v = inflater.inflate(R.layout.view_search_suggestion, parent, false)
            holder.textView = v.findViewById(android.R.id.text1) as TextView
        }

        v.tag = holder
        return v
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val holder = view?.getTag() as ViewHolder
        holder.textView?.setText(cursor?.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)))
    }

    override fun getItemViewType(position: Int): Int {
        val cursor = getItem(position) as Cursor
        if (cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)) == COLUMN_TYPE_HEADER) {
            return 0
        } else {
            return 1
        }
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    class ViewHolder {
        var textView: TextView? = null
    }
}