package com.example.tugasakhirbaru.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.R
import com.example.tugasakhirbaru.databinding.ActivityAdminConfirmPaymentBinding
import com.example.tugasakhirbaru.model.TransactionMenu
import com.example.tugasakhirbaru.util.KotlinExt.getByValue
import com.example.tugasakhirbaru.util.KotlinExt.toHashMap
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.DatabasePath
import com.example.tugasakhirbaru.util.constants.IntentNameExtra
import com.example.tugasakhirbaru.viewmodel.AdminConfirmViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AdminConfirmActivity : AppCompatActivity(), ViewModelListener{
    lateinit var binding: ActivityAdminConfirmPaymentBinding

    private val item by lazy {
        intent.getParcelableExtra<TransactionMenu?>(IntentNameExtra.UPLOAD_EXTRA)
    }

    private val viewModel: AdminConfirmViewModel by lazy {
        AdminConfirmViewModel(
            FirebaseDatabase.getInstance().getReference(DatabasePath.TRANSACTION),
            this
        )
    }

    private val statusMap by lazy {
        resources.getStringArray(R.array.status_pembayaran).toHashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminConfirmPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        item?.let { data ->
            showConfirmPayment(data)
            viewModel.item = data
            val position = statusMap.getByValue(data.statusPembayaran) ?: 0
            binding.spinner.setSelection(position)

        }

        // viewModel.updateStatus()
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position > 0) {
                    viewModel.updateStatusPembayaran(statusMap[position] ?: "")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    fun showConfirmPayment(itemId: TransactionMenu){
//        val progressDialog = ProgressDialog(this)
//        progressDialog.setMessage("Fetching image...")
//        progressDialog.setCancelable(false)
//        progressDialog.show()

        val imageName = itemId.id
        val ref = FirebaseStorage.getInstance().reference.child("image/$imageName")
        val localFile = File.createTempFile("tempImage","jpeg")
        ref.getFile(localFile).addOnSuccessListener {
//            if (progressDialog.isShowing) progressDialog.dismiss()
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            Log.i("tes","data $bitmap")
            binding.previewImg.setImageBitmap(bitmap)
        }.addOnFailureListener{
//            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, R.string.failedFetchingFile,Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {

    }
}