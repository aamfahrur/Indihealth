package com.aamfahrur.indihealth.view.auth.forgot

import android.content.Context
import com.aamfahrur.indihealth.App
import com.aamfahrur.indihealth.R

class ForgotPasswordPresenter (private val view: ForgotPasswordContract.View, private val context: Context) : ForgotPasswordContract.Presenter {

    init {
        view.presenter = this
    }

    override fun resetPassword(email: String) {
        if (email.isEmpty()) {
            view.onError(context.getString(R.string.validation_email_reset))
        } else {
            view.progress(true)

            App.mAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                view.onInfo(String.format(context.getString(R.string.info_email_reset), email))
            }.addOnFailureListener {
                view.onError(it.localizedMessage.toString())
            }
        }
    }

}