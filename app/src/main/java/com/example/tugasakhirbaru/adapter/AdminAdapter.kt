package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.databinding.TransactionItemBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.KotlinExt.openAdminDetailActivity

class AdminAdapter(private val context: Context, private val listener: Listener) :
    RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {
    private val menuList: ArrayList<TransactionMenu> = arrayListOf()

    fun setData(listData: List<TransactionMenu>) {
        menuList.clear()
        menuList.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder =
        AdminViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.item = item
        holder.binding.cardMenuBought.setOnClickListener {
            context.openAdminDetailActivity(item)
        }
    }

    override fun getItemCount(): Int = menuList.size

    class AdminViewHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun updateItem(item: Menu)
        fun removeItem(id: String)
    }
}