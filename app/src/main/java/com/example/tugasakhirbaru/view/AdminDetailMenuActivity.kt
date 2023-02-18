package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.DetailAdminAdapter
import com.example.tugasakhirbaru.databinding.ActivityDetailAdminBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.KotlinExt.getByValue
import com.example.tugasakhirbaru.util.KotlinExt.toHashMap
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.TRANSACTION_EXTRA
import com.example.tugasakhirbaru.viewmodel.AdminDetailMenuViewModel
import com.google.firebase.database.FirebaseDatabase

class AdminDetailMenuActivity : AppCompatActivity(), ViewModelListener,
    DetailAdminAdapter.Listener {
    lateinit var binding: ActivityDetailAdminBinding

    private val item by lazy {
        intent.getParcelableExtra<TransactionMenu?>(TRANSACTION_EXTRA)
    }

    private val adapter: DetailAdminAdapter by lazy {
        DetailAdminAdapter(this, this)
    }

    private val viewModel: AdminDetailMenuViewModel by lazy {
        AdminDetailMenuViewModel(
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION), this
        )
    }

    private val statusMap by lazy {
        resources.getStringArray(R.array.status_transaction).toHashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        binding.listItem.adapter = adapter
        binding.listItem.layoutManager = LinearLayoutManager(this)

        item?.let { data ->
            viewModel.item = data

            adapter.setData(data.orderList.values.toList())

            val position = statusMap.getByValue(data.status) ?: 0
            Log.d("tes", "Status transaksi: ${data.status}")
            Log.d("tes", "Position: $position")
            binding.spinner.setSelection(position)
        }

        // viewModel.updateStatus()
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position > 0) {
                    viewModel.updateStatus(statusMap[position] ?: "")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
    }

    override fun updateItem(item: Menu) {
        TODO("Not yet implemented")
    }

    override fun removeItem(id: String) {
        TODO("Not yet implemented")
    }
}