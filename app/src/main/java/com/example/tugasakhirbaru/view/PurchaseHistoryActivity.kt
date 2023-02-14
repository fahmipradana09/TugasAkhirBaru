package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.HistoryPurchaseAdapter
import com.example.tugasakhirbaru.databinding.ActivityPurchasehistoryBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.KotlinExt.openLoginActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.viewmodel.PurchaseHistoryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PurchaseHistoryActivity : AppCompatActivity(), ViewModelListener, HistoryPurchaseAdapter.Listener {
    lateinit var binding: ActivityPurchasehistoryBinding

    private val viewModel: PurchaseHistoryViewModel by lazy {
        PurchaseHistoryViewModel(
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            FirebaseDatabase.getInstance().getReference(DatabasePath.USER),
            FirebaseAuth.getInstance()
        )
    }

    private val adapter: HistoryPurchaseAdapter by lazy {
        HistoryPurchaseAdapter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchasehistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.viewModel = viewModel
        binding.listItem.adapter = adapter
        binding.listItem.layoutManager = LinearLayoutManager(this)

        viewModel.listData.observe(this){ list ->
            adapter.setData(list)
        }


        viewModel.getMenuData()
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: Menu) {
        TODO("Not yet implemented")
    }

    override fun removeItem(id: String) {
        TODO("Not yet implemented")
    }
}