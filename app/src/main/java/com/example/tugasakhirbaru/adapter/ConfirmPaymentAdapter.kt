package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.DetailAdminItemBinding
import com.example.tugasakhirbaru.databinding.UploadPembayaranItemBinding
import com.example.tugasakhirbaru.model.Menu

class ConfirmPaymentAdapter(private val context: Context) :
    RecyclerView.Adapter<ConfirmPaymentAdapter.UploadPembayaranViewHolder>() {
    private val menuList: ArrayList<Menu> = arrayListOf()

    fun setData(listData: List<Menu>) {
        menuList.clear()
        menuList.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadPembayaranViewHolder =
        UploadPembayaranViewHolder(
            UploadPembayaranItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )

    override fun onBindViewHolder(holder: UploadPembayaranViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.item = item

    }

    override fun getItemCount(): Int = menuList.size

    class UploadPembayaranViewHolder(val binding: UploadPembayaranItemBinding) : RecyclerView.ViewHolder(binding.root)

}