package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.databinding.LayoutNutritionBinding
import com.example.tugasakhirbaru.model.ComponentChecklist

class NutritionAdapter(private val context: Context) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {
    private val list: ArrayList<ComponentChecklist> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutNutritionBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.item = item
    }

    override fun getItemCount(): Int = list.size

    fun setData(dataList: List<ComponentChecklist>) {
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: LayoutNutritionBinding) : RecyclerView.ViewHolder(binding.root)

}