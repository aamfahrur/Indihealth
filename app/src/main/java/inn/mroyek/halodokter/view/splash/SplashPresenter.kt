package inn.mroyek.halodokter.view.splash

import android.content.Context
import inn.mroyek.halodokter.App
import inn.mroyek.halodokter.utils.commons.Logs

class SplashPresenter (private val view: SplashContract.View, private val context: Context) : SplashContract.Presenter {

    init {
        view.presenter = this
    }
    override fun checkSession() {
        App.mAuth.currentUser?.let {
            Logs.i("Sudah Login")
            view.showMain()
        } ?: run {
            Logs.i("belum login")
            view.showSignIn()
        }
    }

}