package com.will.draganddraw.receiver

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.will.draganddraw.databinding.ActivityReceiverBinding

class ReceiverActivity: AppCompatActivity() {
    private lateinit var myReceiver: MyBroadcastReceiver
    private lateinit var viewBinding: ActivityReceiverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
//        myReceiver = MyBroadcastReceiver()
//        val filter = IntentFilter()
//        filter.addAction("com.will.action")
//        registerReceiver(myReceiver, filter)

        viewBinding.btnSend.setOnClickListener {
            val intent = Intent()
            intent.action = "com.will.action"
            // 如果是静态广播接收，发送隐式广播必须带包名
            intent.`package` = packageName
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(myReceiver)
    }
}