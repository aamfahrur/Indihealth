package com.aamfahrur.indihealth.view.user.profile

import android.content.Context
import com.aamfahrur.indihealth.App
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.struct.User
import com.aamfahrur.indihealth.utils.commons.Preferences
import com.aamfahrur.indihealth.utils.extensions.user
import com.aamfahrur.indihealth.utils.services.AmMessagingService

class ProfilePresenter(private val view: ProfileContract.View, private val context: Context) :
    ProfileContract.Presenter {

    init {
        view.presenter = this
    }

    override fun signOut() {
        AmMessagingService().storeOnline(false)
        App.mMessaging.unsubscribeFromTopic(context.getString(R.string.app_channel))
        App.mAuth.signOut()
        Preferences(context).storeUid("")
        Preferences(context).storeToken("")
        view.showSignIn()
    }

    override fun user() {
        App.mAuth.currentUser?.let {
            App.mDatabase.user(it.uid)
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    documentSnapshot?.let { document ->
                        document.toObject(User::class.java)?.let { user ->
                            view.displayUserInfo(user)
                        }
                    }
                    firebaseFirestoreException?.let { e ->
                        view.onError(e.localizedMessage.toString())
                    }
                }
        }
    }

}