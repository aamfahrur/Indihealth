package com.aamfahrur.indihealth.view.auth.signup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.aamfahrur.indihealth.R
import com.aamfahrur.indihealth.base.AmActivity
import com.aamfahrur.indihealth.utils.commons.Constants.INTENT.ACTIVITY.FIRST_LOGIN
import com.aamfahrur.indihealth.view.user.UserMainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AmActivity(R.layout.activity_sign_up), SignUpContract.View {

    override var presenter: SignUpContract.Presenter? = SignUpPresenter(this, this)

    private lateinit var datePicker: DatePickerDialog
    private var uriPhoto: Uri? = null

    override fun initView() {

        initCalendarDialog()
        initToolbar(back= true)
        setPageName("", false)

    }

    @SuppressLint("SetTextI18n")
    private fun initCalendarDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, y, m, d ->
            val mm = m + 1
            inputDateOfBirth.setText("$d/$mm/$y")
        }, year, month, day)

        datePicker.setOnCancelListener {
            inputDateOfBirth.setText("")
        }
    }

    override fun showMain() {
        val intent = Intent(this, UserMainActivity::class.java)

        intent.putExtra(FIRST_LOGIN, true)

        ActivityCompat.startActivity(this, intent, null)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        finishAffinity()
    }

    override fun initListener() {
        buttonJoin.setOnClickListener {

            presenter?.let {
                val fullName = inputFullName.text.toString()
                val email = inputEmailAddress.text.toString()
                val password = inputPassword.text.toString()
                val phone = inputPhoneNumber.text.toString()
                val dateOfBirth = inputDateOfBirth.text.toString()

                if(it.validate(fullName, email, password, phone, dateOfBirth)) {
                    Toast.makeText(this, "okeh valid", Toast.LENGTH_SHORT).show()
                    it.signUp(fullName, email, password, phone, dateOfBirth)
                } else {
                    Toast.makeText(this, "gak valid", Toast.LENGTH_SHORT).show()
                }
            }

        }

        inputDateOfBirth.setOnClickListener {
            datePicker.show()
        }
    }



}