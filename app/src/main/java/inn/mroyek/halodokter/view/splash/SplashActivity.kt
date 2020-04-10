package inn.mroyek.halodokter.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.view.animation.AnimationUtils
import inn.mroyek.halodokter.R
import inn.mroyek.halodokter.base.AmActivity
import inn.mroyek.halodokter.view.auth.login.LoginActivity
import inn.mroyek.halodokter.view.user.UserMainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AmActivity(R.layout.activity_splash), SplashContract.View {

    override var presenter: SplashContract.Presenter? = SplashPresenter(this, this)

    private var timer = Timer()

    override fun initView() {
        splashAnimation()
    }

    private fun splashAnimation() {
        var count = 0
        timer.scheduleAtFixedRate(timerTask {
            display(count++)

            if (count > 5) {
                timer.cancel()
            }
        }, 0, 400)
    }

    @SuppressLint("ResourceType")
    private fun display(count: Int) {
        when(count) {
            0 -> {
                val animate = AnimationUtils.loadAnimation(this, R.animator.bounce)
                imageLogo.animation = animate
            }
            5 -> {
                runOnUiThread {
                    presenter?.checkSession()
                }
            }
            else -> return
        }
    }

    override fun onBackPressed() {
        timer.cancel()
        finish()
    }
    override fun showMain() {
        startActivity(Intent(this, UserMainActivity::class.java))
        finish()
    }

    override fun showSignIn() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}