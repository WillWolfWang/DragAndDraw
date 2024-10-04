package com.will.draganddraw.offline

import android.content.Intent
import android.os.Bundle
import com.will.draganddraw.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity() {
    private lateinit var viewBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.login.setOnClickListener {
            val i = Intent(this, ShowActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}