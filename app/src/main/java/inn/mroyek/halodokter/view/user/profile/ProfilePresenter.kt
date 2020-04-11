package inn.mroyek.halodokter.view.user.profile

import android.content.Context
import inn.mroyek.halodokter.App
import inn.mroyek.halodokter.R
import inn.mroyek.halodokter.struct.User
import inn.mroyek.halodokter.utils.commons.Preferences
import inn.mroyek.halodokter.utils.extensions.user
import inn.mroyek.halodokter.utils.services.AmMessagingService

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