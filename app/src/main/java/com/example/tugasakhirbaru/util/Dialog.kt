package com.example.tugasakhirbaru.helper

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.example.tugasakhirbaru.databinding.ConfirmDialogBinding

object Dialog{
    fun Activity.showDialogSuccess (callback: (()->Unit)){
        val dialogBinding = ConfirmDialogBinding.inflate(layoutInflater)

        dialogBinding.LLConfirm.setOnClickListener {
            finish()
            callback.invoke()
        }
        val scaleAnimation = ScaleAnimation(
            0.4f, 1f,
            0.4f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 200
        dialogBinding.root.startAnimation(scaleAnimation)
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(dialogBinding.root  )
        myDialog.setCancelable(false)
        myDialog.show()

    }

    fun Activity.showConfirmDelete(){

    }
}

