package com.nryoon.mapexample.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nryoon.mapexample.R

class SearchResultListFragment: Fragment() {
    private lateinit var adapter: SearchResultListAdapter
    private var listener: OnSearchItemSelectedListener? = null

    interface OnSearchItemSelectedListener {
        fun onItemSelected(item: SearchResultItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnSearchItemSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = SearchResultListAdapter(object : SearchResultListAdapter.OnItemClickListener {
            override fun onItemClick(item: SearchResultItem) {
                listener?.onItemSelected(item)
            }
        })

        recyclerView.adapter = adapter
    }

    fun updateItems(items: List<SearchResultItem>) {
        adapter.updateList(items)
    }

    class SearchResultListAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<SearchResultListAdapter.ViewHolder>() {
        private val dataSet = mutableListOf<SearchResultItem>()
        interface OnItemClickListener {
            fun onItemClick(item: SearchResultItem)
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val addressNameTextView: TextView
            val addressTextView: TextView
            init {
                addressNameTextView = view.findViewById(R.id.address_name)
                addressTextView = view.findViewById(R.id.address)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.search_result_item_row, viewGroup, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.addressNameTextView.text = dataSet[position].name
            viewHolder.addressTextView.text = dataSet[position].address
            viewHolder.itemView.setOnClickListener {
                listener.onItemClick(dataSet[position])
            }
        }

        override fun getItemCount() = dataSet.size

        fun updateList(items: List<SearchResultItem>) {
            dataSet.clear()
            dataSet.addAll(items)
            notifyDataSetChanged()
        }
    }
}
