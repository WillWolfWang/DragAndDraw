package com.will.draganddraw.offline

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    lateinit var receiver: ForceOfflineReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }


    override fun onResume() {
        super.onResume()
        // 在这里注册，是只让栈顶的 activity 才能接收这条广播
         val intentFilter = IntentFilter()
        intentFilter.addAction("com.will.offline")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }


    inner class ForceOfflineReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("You are forced to offline")
                setCancelable(false)
                setPositiveButton("OK") {dialog, which->
                    ActivityCollector.finishAll()
                    val i = Intent(context, LoginActivity::class.java)
                    context.startActivity(i)
                }
                show()
            }
        }

    }
}