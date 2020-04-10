package inn.mroyek.halodokter.view.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import inn.mroyek.halodokter.R
import inn.mroyek.halodokter.base.AmActivity
import inn.mroyek.halodokter.view.auth.forgot.ForgotPasswordActivity
import inn.mroyek.halodokter.view.auth.signup.SignUpActivity
import inn.mroyek.halodokter.view.user.UserMainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class LoginActivity : AmActivity(R.layout.activity_sign_in), LoginContract.View {

    override var presenter: LoginContract.Presenter? = LoginPresenter(this, this)

    override fun initView() {

    }

    override fun initListener() {
        buttonRegister.setOnClickListener {
            showSignUp()
        }
        buttonForgotPassword.setOnClickListener {
            showForgotPassword()
        }
        buttonLogIn.setOnClickListener {
            presenter?.let {
                val email = inputEmailAddress.text.toString()
                val password = inputPassword.text.toString()

                if (it.validate(email, password)){
                    it.signIn(email, password)
                }
            }
        }
    }

    override fun showMain() {
        startActivity(Intent(this, UserMainActivity::class.java))
        finish()
    }

    private fun showForgotPassword() {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    private fun showSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}
