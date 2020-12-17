package com.aamfahrur.indihealth.view.auth.signup

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.aamfahrur.indihealth.App
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.struct.User
import com.aamfahrur.indihealth.utils.commons.Constants
import com.aamfahrur.indihealth.utils.commons.Preferences
import com.aamfahrur.indihealth.utils.extensions.toDate
import com.aamfahrur.indihealth.utils.extensions.user
import com.aamfahrur.indihealth.utils.services.AmMessagingService


class SignUpPresenter(private val view: SignUpContract.View, private val context: Context) : SignUpContract.Presenter {

    init {
        view.presenter = this
    }

    override fun checkSession() {}

    override fun validate(
        fullName: String,
        email: String,
        password: String,
        phone: String,
        dateOfBirth: String): Boolean {

        val passwordMinLength = context.resources.getInteger(R.integer.validation_password_min)

        if(fullName.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            dateOfBirth.isNotEmpty() &&
            phone.isNotEmpty()
        ) {

            if(password.length < passwordMinLength) {
                val validation = String.format(context.getString(R.string.validation_min_length), passwordMinLength.toString())
                view.onError(validation)
            }else{
                return true
            }

        }else{
            view.onError(context.getString(R.string.validation_required_all_input))
        }


        return false
    }

    override fun signUp(fullName: String, email: String, password: String, phone: String, dateOfBirth: String) {
        view.progress(true)

        App.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                App.mAuth.currentUser?.let {firebaseUser ->
                    storeEmailVerification(firebaseUser)

                    val user = User()

                    user.uid = firebaseUser.uid
                    user.fullName = fullName
                    user.email = email
                    user.phoneNumber = phone
                    user.dateOfBirth = dateOfBirth.toDate()
                    user.category = Constants.USER.PATIENT

                    storeUser(user)
                }
            }else{
                view.onError(it.exception?.message.toString())
            }
        }
    }

    private fun storeUser(user: User) {
        App.mDatabase.user(user.uid).set(user).addOnSuccessListener {
            signIn(user)
        }.addOnFailureListener {
            view.onError(it.message ?: "")
        }
    }

    private fun signIn(user: User) {
        AmMessagingService().storeOnline(true)
        AmMessagingService().storeToken(Preferences(context).getToken())
        App.mMessaging.subscribeToTopic(context.getString(R.string.app_channel))

        Preferences(context).storeUid(user.uid)

        view.showMain()
    }

    private fun storeEmailVerification(user: FirebaseUser) {

        user.sendEmailVerification().addOnCompleteListener {
            if(it.isSuccessful) {
                view.onInfo(String.format(context.getString(R.string.info_email_verification), user.email))
            }else{
                view.onError(it.exception?.message.toString())
            }
        }

    }


}