package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.BR
import com.example.tugasakhirbaru.adapter.ComponentAdapter
import com.example.tugasakhirbaru.databinding.ActivityEditIngredientBinding
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.KotlinExt.openCheckoutActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA
import com.example.tugasakhirbaru.viewmodel.EditMenuViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EditMenuActivity : AppCompatActivity(), ComponentAdapter.Listener, ViewModelListener {
    lateinit var binding: ActivityEditIngredientBinding

    private val item by lazy {
        intent.getParcelableExtra<Menu?>(MENU_EXTRA)
    }

    private val adapter: ComponentAdapter by lazy {
        ComponentAdapter(this, this)
    }

    private val viewModel: EditMenuViewModel by lazy {
        EditMenuViewModel(
            FirebaseAuth.getInstance(),
            FirebaseDatabase.getInstance().getReference(DatabasePath.COMPONENT),
            FirebaseDatabase.getInstance().getReference(DatabasePath.USER_CART),
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        item?.let { data ->
            viewModel.item = data
            if (data.imageExists()) {
                Glide.with(this).load(data.picture).into(binding.ivDetailMakanan)
            }
            adapter.setData(data.detailIngredient)
        }
        viewModel.getIngredient()

        binding.componentList.layoutManager = LinearLayoutManager(this)
        binding.componentList.adapter = adapter
    }

    override fun updateList(list: List<ComponentChecklist>) {
        viewModel.item.detailIngredient.clear()
        viewModel.item.detailIngredient.addAll(list)
        viewModel.notifyPropertyChanged(BR.item)
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if (param == EditMenuViewModel.OPEN_CHECKOUT) {
            openCheckoutActivity()
        }
    }
}