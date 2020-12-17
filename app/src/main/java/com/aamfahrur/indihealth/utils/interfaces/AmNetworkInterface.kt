package com.aamfahrur.indihealth.utils.interfaces

interface AmNetworkInterface {

    fun onSuccess(message: String)

    fun onError(message: String)

    fun onInfo(message: String)

    fun onAlert(message: String, actionText: String, actionListener: Runnable)

    fun progress(isShow: Boolean = true)

}