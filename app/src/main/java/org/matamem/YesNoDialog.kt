package org.matamem

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

class YesNoDialog {

    public fun show(context : Context , onYes : DialogInterface.OnClickListener , onNo : DialogInterface.OnClickListener){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("مطمئن هستید ؟").setPositiveButton("بله"  , onYes)
            .setNegativeButton("خیر" , onNo).show()
    }
}