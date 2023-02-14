package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.adapter.MenuCartAdapter
import com.example.tugasakhirbaru.databinding.ActivityCheckoutBinding
import com.example.tugasakhirbaru.helper.Dialog.showDialogSuccess
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.KotlinExt.openHomeActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.Constants.OPEN_HOME
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.viewmodel.CheckoutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity(), ViewModelListener, MenuCartAdapter.Listener {
    lateinit var binding: ActivityCheckoutBinding

    private val viewModel: CheckoutViewModel by lazy {
        CheckoutViewModel(
            FirebaseAuth.getInstance(),
            FirebaseDatabase.getInstance().getReference(DatabasePath.USER_CART),
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            UserPreference.getInstance(this),
            this
        )
    }

    private val adapter: MenuCartAdapter by lazy {
        MenuCartAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.listMenu.layoutManager = LinearLayoutManager(this)
        binding.listMenu.adapter = adapter

        viewModel.listData.observe(this) { list ->
            adapter.setData(list)
            if (list.isEmpty()){
                binding.tvEmptyList.visibility = View.VISIBLE
            }else{
                binding.tvEmptyList.visibility = View.GONE
            }
        }

        binding.btnBayar.setOnClickListener {
            viewModel.transaction()
            showDialogSuccess(callback = { openHomeActivity() })
        }

        viewModel.getUserCart()

        val userPref = UserPreference.getInstance(this).getUser()
        binding.item = userPref
    }


    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if(param == OPEN_HOME){
            openHomeActivity()
        }
    }
    override fun updateItem(item: com.example.tugasakhirbaru.model.Menu) {
        viewModel.updateItem(item)
    }

    override fun removeItem(id: String) {
        viewModel.removeItem(id)
    }
}