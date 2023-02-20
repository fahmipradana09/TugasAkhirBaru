package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.databinding.ListHistoryTransactionItemBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu

class AdminTransactionAdapter(private val context: Context, private val listener :Listener) :
    RecyclerView.Adapter<AdminTransactionAdapter.AdminTransactionViewHolder>() {
    private val menuList: ArrayList<TransactionMenu> = arrayListOf()
    fun setData(listData: List<TransactionMenu>) {
        menuList.clear()
        menuList.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminTransactionViewHolder =
        AdminTransactionViewHolder(
            ListHistoryTransactionItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AdminTransactionViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.item = item

    }

    override fun getItemCount(): Int = menuList.size

    class AdminTransactionViewHolder(val binding: ListHistoryTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun updateItem(item: Menu)
        fun removeItem(id: String)
    }
}