package inn.mroyek.halodokter.utils.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import inn.mroyek.halodokter.utils.commons.Logs
import inn.mroyek.halodokter.utils.interfaces.AmConnectionInterface

class ConnectionReceiver : BroadcastReceiver() {

    private var callback: AmConnectionInterface? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceive(context: Context, intent: Intent) {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        Logs.i("Internet Checking")

        val builder = NetworkRequest.Builder()

        connMgr.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network?) {
                    super.onAvailable(network)

                    showMessage("Internet Connected")

                }

                override fun onLost(network: Network?) {
                    super.onLost(network)

                    showMessage("No Internet Connection")

                }
            })

    }

    private fun showMessage(message: String) {
        this.callback?.let {
            it.onConnectionChange(message)
            Logs.e(message)
        } ?: run {
            Logs.e("No internet callback")
        }
    }

    fun registerReceiver(receiver: AmConnectionInterface) {
        this.callback = receiver
    }


}