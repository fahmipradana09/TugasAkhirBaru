package com.example.tugasakhirbaru.view

import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.adapter.ConfirmPaymentAdapter
import com.example.tugasakhirbaru.databinding.ActivityConfirmPaymentBinding
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.KotlinExt.toHashMap
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.util.constants.IntentNameExtra
import com.example.tugasakhirbaru.viewmodel.ConfirmViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class ConfirmPaymentActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityConfirmPaymentBinding
    private var imageUri: Uri? = null

    private val item by lazy {
        intent.getParcelableExtra<TransactionMenu?>(IntentNameExtra.UPLOAD_EXTRA)
    }

    private val viewModel: ConfirmViewModel by lazy {
        ConfirmViewModel(
            FirebaseAuth.getInstance(),
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            this
        )
    }

    private val adapter: ConfirmPaymentAdapter by lazy {
        ConfirmPaymentAdapter(this)
    }

    private val statusMap by lazy {
        resources.getStringArray(R.array.status_pembayaran).toHashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.listItem.adapter = adapter
        binding.listItem.layoutManager = LinearLayoutManager(this)

        item?.let { data ->
            viewModel.item = data
            adapter.setData(data.orderList.values.toList())
            binding.sendButton.setOnClickListener {
                insertData(data)
            }
            binding.btnGallery.setOnClickListener {
                insertImage()
            }
        }

    }

    private fun insertImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.previewImg.setImageURI(imageUri)
        }
    }

    fun insertData(itemId: TransactionMenu) {
        if (imageUri == null){
            Toast.makeText(this, R.string.emptyFile, Toast.LENGTH_SHORT).show()
            return
        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = itemId.id
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")

        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                Toast.makeText(this, R.string.successfullyUploaded, Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(this, R.string.successfullyUploaded, Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }
    }


    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }
}