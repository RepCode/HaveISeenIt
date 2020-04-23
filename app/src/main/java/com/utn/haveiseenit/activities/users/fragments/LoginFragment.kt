package com.utn.haveiseenit.activities.users.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

import com.utn.haveiseenit.R
import com.utn.haveiseenit.database.appDatabase
import com.utn.haveiseenit.forms.Form
import com.utn.haveiseenit.services.UserAuthentication

class Login : Fragment() {
    private lateinit var v: View
    private var db: appDatabase? = null
    private lateinit var loginForm: Form
    private lateinit var authService: UserAuthentication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        authService = UserAuthentication(db!!.userDao())
        setNavigateToCreateAccount()
        loginForm = Form(requireContext())
            .addEmailInput(v.findViewById(R.id.login_email_input), "email")
            .addPasswordInput(
                v.findViewById(R.id.login_password_input),
                "password",
                validatePattern = false,
                isLast = true
            ).addSubmitButton(v.findViewById(R.id.login_submit_button), onSubmit)
    }

    private fun setNavigateToCreateAccount(){
        v.findViewById<TextView>(R.id.login_to_create_account_hyperlink).setOnClickListener{
            v.findNavController().navigate(LoginDirections.actionLoginToCreateAccountFragment())
        }
    }

    private val onSubmit = {
        if (loginForm.isValid()) {
            authService.login(
                loginForm.getValue("email"),
                loginForm.getValue("password")
            ) { _, error ->
                if (error == null) {
                    v.findNavController().navigate(LoginDirections.actionLoginToMovieNavigation())
                    v.findViewById<TextView>(R.id.login_failed_text).visibility = View.INVISIBLE
                } else {
                    v.findViewById<TextView>(R.id.login_failed_text).visibility = View.VISIBLE
                }
            }
        }
    }
}
