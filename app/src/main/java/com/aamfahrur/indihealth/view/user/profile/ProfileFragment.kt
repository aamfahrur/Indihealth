package com.aamfahrur.indihealth.view.user.profile

import android.content.Intent
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.base.AmFragment
import com.aamfahrur.indihealth.struct.User
import com.aamfahrur.indihealth.utils.commons.Utils
import com.aamfahrur.indihealth.view.auth.login.LoginActivity
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