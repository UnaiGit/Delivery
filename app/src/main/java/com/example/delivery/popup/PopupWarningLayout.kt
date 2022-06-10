package com.example.delivery.popup

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import com.example.delivery.R

object PopupWarningLayout {
    fun showPopup(context: Context, id: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_popup_warning)
        val tvId = dialog.findViewById<TextView>(R.id.tvErrortype)
        tvId.text = id
        val btnClose = dialog.findViewById<ImageButton>(R.id.btnClosePopup)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}