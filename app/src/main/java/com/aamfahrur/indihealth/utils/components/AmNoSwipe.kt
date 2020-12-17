package com.aamfahrur.indihealth.utils.components

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar


internal class AmNoSwipe : BaseTransientBottomBar.Behavior() {
    override fun canSwipeDismissView(child: View):Boolean {
        return false
    }
}