package com.aamfahrur.indihealth.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.aamfahrur.indihealth.utils.commons.Utils
import com.aamfahrur.indihealth.utils.interfaces.AmNetworkInterface
import kotlinx.android.synthetic.main.activity_user_main.*

abstract class AmFragment (@LayoutRes var layout: Int, @StringRes var titleDefault: Int? = null) : Fragment(), AmNetworkInterface {

    var amActivity: AmActivity? = null

    protected abstract fun initView()

    protected open fun initListener() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is AmActivity){
            amActivity = activity as AmActivity
        }
        initView()
        initListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    fun setTitle(title: String? = null, line: Boolean = true, gravity: Int){
        amActivity?.let { activity ->
        }
    }

    override fun onAlert(message: String, actionText: String, actionListener: Runnable) {
        amActivity?.let { activity ->
            layoutParent?.let {
                Utils().showSnackBar(activity, it, message, Snackbar.LENGTH_INDEFINITE, actionText, actionListener)
            }
        }
    }

    override fun onError(message: String) {
        amActivity?.let { activity ->
            layoutParent?.let {
                Utils().showSnackBar(activity, it, message)
            }
        }
    }

    override fun onSuccess(message: String) {
        amActivity?.let { activity ->
            layoutParent?.let {
                Utils().showSnackBar(activity, it, message, Snackbar.LENGTH_LONG)
            }
        }
    }

    override fun onInfo(message: String) {
        amActivity?.let { activity ->
            layoutParent?.let {
                Utils().showSnackBar(activity, it, message)
            }
        }
    }

    override fun progress(isShow: Boolean) {
        amActivity?.let { activity ->
            activity.progress(isShow)
        }
    }
}