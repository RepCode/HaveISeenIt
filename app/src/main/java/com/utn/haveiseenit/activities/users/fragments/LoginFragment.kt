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

class Login : Fragment() {
    private lateinit var v: View
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
        setNavigateToCreateAccount()
        setNavigateToMoviesList()
    }

    private fun setNavigateToCreateAccount(){
        v.findViewById<TextView>(R.id.login_to_create_account_hyperlink).setOnClickListener{
            v.findNavController().navigate(LoginDirections.actionLoginToCreateAccountFragment())
        }
    }

    // TODO: this will be taken by the form service
    private fun setNavigateToMoviesList(){
        v.findViewById<Button>(R.id.login_submit_button).setOnClickListener {
            v.findNavController().navigate(LoginDirections.actionLoginToMovieNavigation())
        }
    }
}
