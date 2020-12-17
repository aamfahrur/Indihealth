package com.aamfahrur.indihealth.view.splash

import android.content.Context
import com.aamfahrur.indihealth.App
import com.aamfahrur.indihealth.utils.commons.Logs

class SplashPresenter (private val view: SplashContract.View, private val context: Context) : SplashContract.Presenter {

    init {
        view.presenter = this
    }
    override fun checkSession() {
        App.mAuth.currentUser?.let {
            Logs.i("Sudah Login")
            view.showMain()
        } ?: run {
            Logs.i("belum login")
            view.showSignIn()
        }
    }

}