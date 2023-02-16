package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.MenuItemHorizontalBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.KotlinExt.openDetailActivity

class HorizontalMenuAdapter(private val context: Context) :
    RecyclerView.Adapter<HorizontalMenuAdapter.MenuViewHorizontalHolder>() {
    private val menuList: ArrayList<Menu> = arrayListOf()

    fun setData(listData: List<Menu>) {
        menuList.clear()
        menuList.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHorizontalHolder =
        MenuViewHorizontalHolder(
            MenuItemHorizontalBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MenuViewHorizontalHolder, position: Int) {
        val item = menuList[position]
        holder.binding.item = item

        if (item.imageExists()) {
            Glide.with(context).load(item.picture).into(holder.binding.imageFood)
        }

        holder.binding.cardFood.setOnClickListener {
            context.openDetailActivity(item)
        }
    }

    override fun getItemCount(): Int = menuList.size

    class MenuViewHorizontalHolder(val binding: MenuItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root)
}