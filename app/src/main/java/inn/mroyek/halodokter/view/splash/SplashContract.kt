package inn.mroyek.halodokter.view.splash

import inn.mroyek.halodokter.base.AmPresenter
import inn.mroyek.halodokter.base.AmView

interface SplashContract {
    interface View : AmView<Presenter> {
        fun showMain()
        fun showSignIn()
    }
    interface Presenter : AmPresenter {
        fun checkSession()
    }
}