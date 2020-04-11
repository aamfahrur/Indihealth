package inn.mroyek.halodokter.view.user.profile

import android.content.Intent
import inn.mroyek.halodokter.R
import inn.mroyek.halodokter.base.AmFragment
import inn.mroyek.halodokter.struct.User
import inn.mroyek.halodokter.utils.commons.Utils
import inn.mroyek.halodokter.view.auth.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : AmFragment(R.layout.fragment_profile, R.string.title_profile), ProfileContract.View {
    override var presenter: ProfileContract.Presenter? = null

    override fun initView() {
        presenter = amActivity?.let {
            ProfilePresenter(this, it)
        }
        presenter?.user()
    }

    override fun displayUserInfo(user: User) {
        with(user) {
            labelName?.text = fullName
            labelPhone?.text = phoneNumber
            ratingBar?.rating = rating
            labelEmail?.text = email
            labelBloodType?.text = blood
            labelSex?.text = sex

            amActivity?.let { context ->
                imageProfilePicture?.let { image ->
                    Utils().imageCircle(image, photoUrl, context)
                }
            }
        }
    }

    override fun showSignIn() {
        amActivity?.let {
            startActivity(Intent(it, LoginActivity::class.java))
            it.finish()
        }
    }

    override fun initListener() {
        buttonSignOut.setOnClickListener {
            presenter?.signOut()
        }
        buttonEdit?.setOnClickListener {
            // TODO
        }
    }
}