package com.aamfahrur.indihealth.view.auth.forgot

import com.aamfahrur.indihealth.base.AmPresenter
import com.aamfahrur.indihealth.base.AmView

interface ForgotPasswordContract {
    interface View : AmView<Presenter> {
        fun clearForm()
    }
    interface Presenter : AmPresenter {
        fun resetPassword(email: String)
    }
}