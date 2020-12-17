package com.aamfahrur.indihealth.view.auth.login

import android.content.Context
import com.aamfahrur.indihealth.App
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.utils.commons.Preferences
import com.aamfahrur.indihealth.utils.services.AmMessagingService

class LoginPresenter(private val view: LoginContract.View, private val context: Context) :
    LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun checkSession() {

    }

    override fun validate(email: String, password: String): Boolean {
        if (email.isEmpty() && password.isEmpty()) {
            view.onError(context.getString(R.string.validation_required_all_input))
            return false
        }
        return true
    }

    override fun signIn(email: String, password: String) {
        view.progress(true)

        App.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            view.progress(false)
            if (it.isSuccessful) {
                App.mAuth.currentUser?.let { user ->
                    AmMessagingService().storeOnline(true)
                    AmMessagingService().storeToken(Preferences(context).getToken())
                    App.mMessaging.subscribeToTopic(context.getString(R.string.app_channel))

                    Preferences(context).storeUid(user.uid)
                    view.showMain()
                }
            } else {
                view.onError(it.exception?.message.toString())
            }
        }
    }

}