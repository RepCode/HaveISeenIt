package com.utn.haveiseenit.activities.users.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.utn.haveiseenit.R

class CreateAccountFragment : Fragment() {
    private lateinit var v: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_create_account, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        setLinkToLogin()
        setArrowBack()
    }

    private fun setLinkToLogin(){
        v.findViewById<TextView>(R.id.create_account_to_login_link).setOnClickListener{
            navigateToLogin()
        }
    }

    private fun setArrowBack(){
        v.findViewById<FloatingActionButton>(R.id.create_account_arrow_back).setOnClickListener{
            navigateToLogin()
        }
    }

    private fun navigateToLogin(){
        v.findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToLogin())
    }
}
