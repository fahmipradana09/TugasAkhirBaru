package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.CheckoutItemBinding
import com.example.tugasakhirbaru.model.MenuCart

class MenuCartAdapter(
    private val context: Context,
    private val listener: Listener
) :
    RecyclerView.Adapter<MenuCartAdapter.CheckoutViewHolder>() {
    private val list: ArrayList<MenuCart> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder =
        CheckoutViewHolder(
            CheckoutItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val item = list[position]
        holder.binding.item = item

        holder.binding.addCountMenu.setOnClickListener {
            item.plus()
            listener.updateItem(item)
            holder.binding.item = item
        }

        holder.binding.minesCountMenu.setOnClickListener {
            item.minus()
            listener.updateItem(item)
            holder.binding.item = item
        }

        holder.binding.deleteButton.setOnClickListener {
            list.removeAt(position)
            listener.removeItem(item.id)
            notifyItemRemoved(position)
        }

        if (item.imageExists()) {
            Glide.with(context).load(item.picture).into(holder.binding.ivKeranjangMakanan)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(dataList: List<MenuCart>) {
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }

    class CheckoutViewHolder(val binding: CheckoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun updateItem(item: MenuCart)
        fun removeItem(id: String)
    }
}