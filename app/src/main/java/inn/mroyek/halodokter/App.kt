package inn.mroyek.halodokter

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.storage.FirebaseStorage

class App : MultiDexApplication() {
    companion object {
        lateinit var mAuth: FirebaseAuth
        lateinit var mDatabase: FirebaseFirestore
        lateinit var mStorage: FirebaseStorage
        lateinit var mMessaging: FirebaseMessaging
        lateinit var mPerformance: FirebasePerformance
    }

    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseFirestore.getInstance()
        mStorage = FirebaseStorage.getInstance()
        mMessaging = FirebaseMessaging.getInstance()
        mPerformance = FirebasePerformance.getInstance()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}