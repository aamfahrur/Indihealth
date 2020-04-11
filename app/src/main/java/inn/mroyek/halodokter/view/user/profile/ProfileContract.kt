package inn.mroyek.halodokter.view.user.profile

import inn.mroyek.halodokter.base.AmPresenter
import inn.mroyek.halodokter.base.AmView
import inn.mroyek.halodokter.struct.User

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