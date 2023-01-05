package com.example.tugasakhirbaru.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.RecyclerViewClickListener

class MenuAdapter(private val menuList: ArrayList<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){


    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentitem = menuList[position]
        holder.tvJudulMakanan.text = currentitem.menu
        holder.tvHargaMakanan.text = currentitem.price
//        holder.deskripsi.text = currentitem.description
        holder.listMenu.setOnClickListener {
            listener?.onItemClicked(it, currentitem)
            Log.i("tes", "ini isi $listener")
        }

    }

    override fun getItemCount(): Int {
        return menuList.size

    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudulMakanan: TextView = itemView.findViewById(R.id.tvJudulMakanan)
        //        val deskripsi: TextView = itemView.findViewById(R.id.deskripsi)
        val tvHargaMakanan: TextView = itemView.findViewById(R.id.tvHargaMakanan)
        val listMenu: LinearLayout = itemView.findViewById(R.id.cardFood)

    }


}