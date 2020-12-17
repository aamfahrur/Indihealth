package com.aamfahrur.indihealth.view.splash

import com.aamfahrur.indihealth.base.AmPresenter
import com.aamfahrur.indihealth.base.AmView

interface SplashContract {
    interface View : AmView<Presenter> {
        fun showMain()
        fun showSignIn()
    }
    interface Presenter : AmPresenter {
        fun checkSession()
    }
}