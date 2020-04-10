package inn.mroyek.halodokter.view.auth.forgot

import inn.mroyek.halodokter.base.AmPresenter
import inn.mroyek.halodokter.base.AmView

interface ForgotPasswordContract {
    interface View : AmView<Presenter> {
        fun clearForm()
    }
    interface Presenter : AmPresenter {
        fun resetPassword(email: String)
    }
}