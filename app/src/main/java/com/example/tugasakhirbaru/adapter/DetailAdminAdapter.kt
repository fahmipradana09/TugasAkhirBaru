package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.DetailAdminItemBinding
import com.example.tugasakhirbaru.model.Menu

class DetailAdminAdapter(private val context: Context, private val listener: Listener) :
    RecyclerView.Adapter<DetailAdminAdapter.DetailAdminViewHolder>() {
    private val menuList: ArrayList<Menu> = arrayListOf()

    fun setData(listData: List<Menu>) {
        menuList.clear()
        menuList.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdminViewHolder =
        DetailAdminViewHolder(
            DetailAdminItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: DetailAdminViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.item = item
        if (item.imageExists()){
            Glide.with(context).load(item.picture).into(holder.binding.ivKeranjangMakanan)
        }

    }

    override fun getItemCount(): Int = menuList.size

    class DetailAdminViewHolder(val binding: DetailAdminItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun updateItem(item: Menu)
        fun removeItem(id: String)
    }
}