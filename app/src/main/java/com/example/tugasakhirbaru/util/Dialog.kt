    package com.example.tugasakhirbaru.util

    import android.app.Activity
    import android.app.Dialog
    import android.view.Window
    import android.view.animation.Animation
    import android.view.animation.ScaleAnimation
    import com.example.tugasakhirbaru.databinding.ActivityAdminConfirmPaymentBinding
    import com.example.tugasakhirbaru.databinding.ConfirmDialogBinding
    import com.example.tugasakhirbaru.databinding.DenialDialogBinding
    import com.example.tugasakhirbaru.util.Dialog.showDialogSuccess

    object Dialog{
        fun Activity.dismissDialog() {
            dialog?.dismiss()
            dialog = null
        }

        private var dialog: Dialog? = null
        fun Activity.showDialogSuccess (callback: (()->Unit)){
            val dialogBinding = ConfirmDialogBinding.inflate(layoutInflater)

            dialogBinding.LLConfirm.setOnClickListener {
                finish()
                callback.invoke()
                dismissDialog()
            }

            val scaleAnimation = ScaleAnimation(
                0.4f, 1f,
                0.4f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )

            scaleAnimation.duration = 200
            dialogBinding.root.startAnimation(scaleAnimation)

            dialog = Dialog(this)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(dialogBinding.root)
            dialog?.setCancelable(false)
            dialog?.show()
        }


        fun Activity.showDialogDenial(callback: () -> Unit){
            val dialogBinding = DenialDialogBinding.inflate(layoutInflater)
            dialogBinding.LLDenial.setOnClickListener {
                finish()
                callback.invoke()
                dismissDialog()
            }
            val scaleAnimation = ScaleAnimation(
                0.4f, 1f,
                0.4f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 200
            dialogBinding.root.startAnimation(scaleAnimation)

            dialog = Dialog(this)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(dialogBinding.root)
            dialog?.setCancelable(false)
            dialog?.show()
        }
    }

