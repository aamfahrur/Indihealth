package com.aamfahrur.indihealth.view.user

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.base.AmActivity
import com.aamfahrur.indihealth.utils.commons.Constants
import com.aamfahrur.indihealth.utils.extensions.selectedListener
import com.aamfahrur.indihealth.utils.services.AmMessagingService
import com.aamfahrur.indihealth.view.user.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_user_main.*
import kotlinx.android.synthetic.main.component_toolbar.*

class UserMainActivity : AmActivity(R.layout.activity_user_main), BottomNavigationView.OnNavigationItemSelectedListener {

    private val home = Fragment()
    private val profile = ProfileFragment()
    private val chatt = Fragment()

    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = home

    override fun initView() {
        initToolbar(pageName = getString(R.string.title_home))
        iniBottomNavigation()
        initFragment()
    }

    override fun initListener() {
        componentBottomMenu selectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    private fun iniBottomNavigation() {
        componentBottomMenu.itemIconTintList = null
    }

    private fun initFragment() {
        fragmentManager.beginTransaction().add(R.id.frameLayout, profile, Constants.FRAGMENT.PROFILE.NAME).hide(profile).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, chatt, Constants.FRAGMENT.CHAT.NAME).hide(chatt).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, home, Constants.FRAGMENT.HOME.NAME).commit()

        componentBottomMenu.selectedItemId = R.id.home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        resetMenu()

        val state = when(item.itemId){
            R.id.home -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(home).commit()
                activeFragment = home
                appendMenu(R.menu.home)

                true
            }
            R.id.profile -> {
                supportActionBar?.let { actionBar ->
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_qr)
                }
                fragmentManager.beginTransaction().hide(activeFragment).show(profile).commit()
                activeFragment = profile
                appendMenu(R.menu.profile)

                true
            }
            R.id.chat -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(profile).commit()
                activeFragment = chatt
                appendMenu(R.menu.chat)
                setToolbarSearch(true)

                true
            }
            else -> {
                false
            }
        }
        /*if(item.itemId == R.id.profile) {
           activeFragment.setTitle(gravity= Gravity.CENTER)
       }else{
           activeFragment.setTitle(gravity= Gravity.START)
       }*/
        return state
    }

    private fun appendMenu(@MenuRes menu: Int) {
        toolbar.inflateMenu(menu)
    }

    private fun resetMenu() {
        setToolbarSearch(false)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron)
        }
        toolbar.menu.removeItem(R.id.schedule)
        toolbar.menu.removeItem(R.id.love)
    }

    override fun onBackPressed() {
        AmMessagingService().storeOnline(false)
        super.onBackPressed()
    }
}
