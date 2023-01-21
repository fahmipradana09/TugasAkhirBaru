package com.example.tugasakhirbaru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.databinding.LayoutComponentBinding
import com.example.tugasakhirbaru.model.ComponentChecklist

class ComponentAdapter(private val context: Context, private val listener: Listener) :
    RecyclerView.Adapter<ComponentAdapter.ViewHolder>() {
    private val list: ArrayList<ComponentChecklist> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutComponentBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.item = item
        holder.binding.checkBox.isChecked = item.isChecked
        holder.binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            item.isChecked = isChecked
            listener.updateList(list)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(dataList: List<ComponentChecklist>) {
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: LayoutComponentBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun updateList(list: List<ComponentChecklist>)
    }
}