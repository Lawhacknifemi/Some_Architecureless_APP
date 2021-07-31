package com.example.blingytest

import android.R
import androidx.fragment.app.DialogFragment



class CustomisedDialogFragment : DialogFragment() {

    fun newInstance(): CustomisedDialogFragment? {
        val f1 = CustomisedDialogFragment()
        f1.setStyle(STYLE_NO_FRAME, R.style.Theme_DeviceDefault_Dialog)
        return f1
    }

}