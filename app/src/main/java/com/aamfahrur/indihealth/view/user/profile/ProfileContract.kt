package com.aamfahrur.indihealth.view.user.profile

import com.aamfahrur.indihealth.base.AmPresenter
import com.aamfahrur.indihealth.base.AmView
import com.aamfahrur.indihealth.struct.User

interface ProfileContract {
    interface View : AmView<Presenter> {
        fun showSignIn()
        fun displayUserInfo(user: User)
    }

    interface Presenter : AmPresenter {
        fun signOut()
        fun user()
    }
}