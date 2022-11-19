package com.example.wheelz.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.wheelz.R
import com.example.wheelz.firestore.FireStoreClass
import com.example.wheelz.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_forgot_password.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_register.setOnClickListener(this)

        //setupActionBar()

    }
//   Grizimas atgal
//    private fun setupActionBar() {
//
//        setSupportActionBar(toolbar_login_activity)
//        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
//        }
//        toolbar_login_activity.setNavigationOnClickListener { onBackPressed() }
//    }

     override fun onClick(view: View?) {
         if (view != null) {
             when (view.id) {
                 R.id.tv_forgot_password -> {
                     val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                     startActivity(intent)
                 }

                 R.id.btn_login -> {
                     logInRegisteredUser()

                 }

                 R.id.tv_register -> {
                     val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                 }
             }
         }
     }


    //TODO galbut klaida
    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()

        Log.i("${resources.getString(R.string.first_name)}: ", user.firstName)
        Log.i("${resources.getString(R.string.last_name)}: ", user.lastName)
        Log.i("${resources.getString(R.string.email)}: ", user.email)

        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


    private fun validateLoginDetails(): Boolean {
        return when{
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {

                true
            }
        }
    }

    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        FireStoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }

        }
    }
}