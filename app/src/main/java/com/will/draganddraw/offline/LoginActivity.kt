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

        val prefs = getPreferences(MODE_PRIVATE)

        val isRemember = prefs.getBoolean("remember", false)
        if (isRemember) {
            val account = prefs.getString("account", "")
            val password = prefs.getString("password", "")
            viewBinding.accountEdit.setText(account)
            viewBinding.passwordEdit.setText(password)
            viewBinding.rememberPass.isChecked = true
        }

        viewBinding.login.setOnClickListener {
            val account = viewBinding.accountEdit.text.toString().trim()
            val password = viewBinding.passwordEdit.text.toString().trim()

            val editor = prefs.edit()
            if (viewBinding.rememberPass.isChecked) {
                editor.putString("account", account)
                editor.putString("password", password)
                editor.putBoolean("remember", true)
            } else {
                editor.clear()
            }
            editor.apply()

            val i = Intent(this, ShowActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}