package inn.mroyek.halodokter.view.auth.signup

import inn.mroyek.halodokter.base.AmPresenter
import inn.mroyek.halodokter.base.AmView

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