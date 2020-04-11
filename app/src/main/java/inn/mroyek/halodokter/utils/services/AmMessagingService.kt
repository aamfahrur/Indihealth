package inn.mroyek.halodokter.utils.services


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import inn.mroyek.halodokter.App
import inn.mroyek.halodokter.R
import inn.mroyek.halodokter.struct.Location
import inn.mroyek.halodokter.struct.User
import inn.mroyek.halodokter.utils.commons.Logs
import inn.mroyek.halodokter.utils.commons.Preferences
import inn.mroyek.halodokter.utils.extensions.location
import inn.mroyek.halodokter.utils.extensions.user
import inn.mroyek.halodokter.view.user.UserMainActivity

class AmMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logs.firebase("From : ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.run {
            sendNotification(
                remoteMessage.data["description"] ?: "",
                remoteMessage.data["title"] ?: "",
                (0..500).random()
            )
        }
    }

    override fun onNewToken(token: String) {
        Logs.firebase("Refreshed token $token")
        storeToken(token)
        Preferences(this).storeToken(token)
    }

    fun storeToken(token: String) {
        if (token.isNotEmpty()){
            val data : MutableMap<String, Any> = mutableMapOf()
            data[User.CREATOR.Fields.pushId] = token
            data[User.CREATOR.Fields.lastUpdated] = Timestamp.now()

            App.mAuth.currentUser?.let { user ->
                App.mDatabase.user(user.uid).update(data.toMap())
                    .addOnFailureListener {
                        Logs.firebase("Failed update token ${it.message}")
                    }
            }
        }
    }

    private fun sendNotification(body: String, title: String, from: Int) {
        val intent = Intent(this, UserMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0 /*Request code */,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.app_channel)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(from, notificationBuilder.build())
    }

    fun storeLocation(location: GeoPoint) {
        App.mAuth.currentUser?.let { user ->
            val data : MutableMap<String, Any> = mutableMapOf()
            data[User.CREATOR.Fields.location] = location

            App.mDatabase.user(user.uid).update(data.toMap()).addOnSuccessListener {
                Logs.firebase("last location, ${location.latitude} | ${location.longitude}")
            }.addOnFailureListener {
                Logs.firebase("failed update state ${it.message}")
            }

            App.mDatabase.location().add(Location(location = location, uid = user.uid))
        }
    }

    fun storeOnline(state: Boolean) {
        App.mAuth.currentUser?.let { user ->
            val data : MutableMap<String, Any> = mutableMapOf()
            data[User.CREATOR.Fields.onlineUser] = state
            data[User.CREATOR.Fields.lastUpdated] = Timestamp.now()

            App.mDatabase.user(user.uid).update(data.toMap()).addOnSuccessListener {
                Logs.firebase("State changed to $state")
            }.addOnFailureListener {
                Logs.firebase("failed update state: ${it.message}")
            }
        }
    }
}