package inn.mroyek.halodokter.view.auth.login

import inn.mroyek.halodokter.base.AmPresenter
import inn.mroyek.halodokter.base.AmView

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