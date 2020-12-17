package com.aamfahrur.indihealth.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import com.kaopiz.kprogresshud.KProgressHUD
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.utils.commons.Constants.INTENT.REQUEST.PERMISSION
import com.aamfahrur.indihealth.utils.commons.Dialogs
import com.aamfahrur.indihealth.utils.commons.Logs
import com.aamfahrur.indihealth.utils.commons.Utils
import com.aamfahrur.indihealth.utils.interfaces.AmConnectionInterface
import com.aamfahrur.indihealth.utils.interfaces.AmNetworkInterface
import com.aamfahrur.indihealth.utils.services.AmMessagingService
import com.aamfahrur.indihealth.utils.services.ConnectionReceiver
import com.aamfahrur.indihealth.view.user.UserMainActivity
import kotlinx.android.synthetic.main.activity_user_main.*
import kotlinx.android.synthetic.main.component_toolbar.*


@SuppressLint("Registered")
abstract class AmActivity(@LayoutRes var layout: Int) : AppCompatActivity(), AmConnectionInterface,
    AmNetworkInterface {

    private var connectionReceiver: ConnectionReceiver? = null
    private var progressBar: KProgressHUD? = null
    private var location: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private var isRegisteredReceiver: Boolean = false

    protected abstract fun initView()

    protected open fun initListener() {}

    fun initToolbar(
        back: Boolean = false,
        pageName: String = "",
        primary: Boolean = false
    ) {
        setSupportActionBar(toolbar)

        setPageName(pageName)

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(back)

            val backIcon = ContextCompat.getDrawable(this, R.drawable.ic_chevron)
            val iconColor = PorterDuffColorFilter(ContextCompat.getColor(this,
                if(primary) R.color.colorTextAccent else R.color.colorTextPrimary), PorterDuff.Mode.MULTIPLY)

            backIcon?.let {
                it.colorFilter = iconColor
                toolbar.overflowIcon?.colorFilter = iconColor
            }

            actionBar.setHomeAsUpIndicator(backIcon)
        }
    }

    fun setTitleGravity(gravity: Int) {
        val layoutParams = toolbarTitle.layoutParams as Toolbar.LayoutParams

        layoutParams.gravity = gravity

        toolbarTitle.layoutParams = layoutParams
    }

    fun setToolbarSearch(state: Boolean) {
        toolbarSearch.visibility = if(state) View.VISIBLE else View.GONE
    }

    fun setPageName(pageName: String, line: Boolean = true) {
        toolbarTitle.text = pageName
        lineDivider.setBackgroundColor(ContextCompat.getColor(this,
            if(line) R.color.colorDivider else R.color.colorBackgroundSecondary))
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isRegisteredReceiver) {
            unregisterReceiver(connectionReceiver)
            isRegisteredReceiver = false
        }
    }

    override fun onPause() {
        super.onPause()
        location?.removeLocationUpdates(locationCallback)

        if(isRegisteredReceiver) {
            unregisterReceiver(connectionReceiver)
            isRegisteredReceiver = false
        }
    }

    override fun onStart() {
        super.onStart()

        startLocationUpdates()
    }

    override fun onResume() {
        super.onResume()

        startLocationUpdates()
    }

    private fun initData(receiver: AmConnectionInterface) {
        if(connectionReceiver == null) {
            connectionReceiver = ConnectionReceiver()
            connectionReceiver?.let {
                val mIntentFilter = IntentFilter()

                it.registerReceiver(receiver)
                mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")

                registerReceiver(connectionReceiver, mIntentFilter)
                isRegisteredReceiver = true
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initData(this)

        super.onCreate(savedInstanceState)

        progressBar = Dialogs().initProgressDialog(this)

        if(this == UserMainActivity::class.java) {
            initLocation()
        }

        setContentView(layout)

        initView()
        initListener()
    }

    private fun initLocation() {
        if (isGrantedPermissions()) {
            location = LocationServices.getFusedLocationProviderClient(this)

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.let { lastLocation ->
                        AmMessagingService().storeLocation(GeoPoint(lastLocation.lastLocation.latitude, lastLocation.lastLocation.longitude))
                    }
                }
            }

        }else {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission_group.CAMERA, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest()

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000

        location?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun isGrantedPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }

    override fun progress(isShow: Boolean) {
        progressBar?.let {
            if(isShow && !it.isShowing) {
                it.show()
            }else{
                it.dismiss()
            }
        }
    }

    override fun onConnectionChange(message: String) {
        Logs.e(message)
    }

    override fun onAlert(message: String, actionText: String, actionListener: Runnable) {
        progress(false)

        layoutParent?.let {
            Utils().showSnackBar(this, it, message, Snackbar.LENGTH_INDEFINITE, actionText, actionListener)
        }
    }

    override fun onError(message: String) {
        progress(false)

        layoutParent?.let {
            Utils().showSnackBar(this, it, message)
        }
    }

    override fun onSuccess(message: String) {
        progress(false)

        layoutParent?.let {
            Utils().showSnackBar(this, it, message, Snackbar.LENGTH_LONG)
        }
    }

    override fun onInfo(message: String) {
        progress(false)

        layoutParent?.let {
            Utils().showSnackBar(this, it, message)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION) {
            initLocation()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}