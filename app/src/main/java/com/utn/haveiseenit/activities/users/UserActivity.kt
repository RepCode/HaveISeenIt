package com.utn.haveiseenit.activities.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utn.haveiseenit.R

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}
