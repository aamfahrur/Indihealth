package com.aamfahrur.indihealth.view.auth.login

import com.aamfahrur.indihealth.base.AmPresenter
import com.aamfahrur.indihealth.base.AmView

interface LoginContract {
    interface View: AmView<Presenter> {
        fun showMain()
    }
    interface Presenter : AmPresenter {
        fun checkSession()
        fun validate(email: String, password: String) : Boolean
        fun signIn(email: String, password: String)
    }
}