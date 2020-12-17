package com.aamfahrur.indihealth.view.auth.signup

import com.aamfahrur.indihealth.base.AmPresenter
import com.aamfahrur.indihealth.base.AmView

interface SignUpContract {
    interface View : AmView<Presenter> {
        fun showMain()
    }
    interface Presenter : AmPresenter {
        fun checkSession()
        fun validate(fullName: String, email: String, password: String, phone: String, dateOfBirth: String) : Boolean
        fun signUp(fullName: String, email: String, password: String, phone: String, dateOfBirth: String)
    }
}