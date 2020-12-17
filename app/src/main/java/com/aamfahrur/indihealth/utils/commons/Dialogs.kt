package com.aamfahrur.indihealth.utils.commons

import android.content.Context
import androidx.core.content.ContextCompat
import com.kaopiz.kprogresshud.KProgressHUD
import com.aamfahrur.indihealth.R

class Dialogs {
    fun initProgressDialog(context: Context): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setDimAmount(0.2f)
            .setCornerRadius(4f)
            .setSize(45,45)
            .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            .setAnimationSpeed(2)
    }
}