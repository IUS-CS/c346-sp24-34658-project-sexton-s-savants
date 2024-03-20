package com.quark.client.components

import android.app.AlertDialog
import android.content.Context

fun showErrorDialog(context: Context, message: String){
    AlertDialog.Builder(context)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}