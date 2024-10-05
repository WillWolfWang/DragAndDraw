package com.will.draganddraw.work

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.will.draganddraw.databinding.ActivityWorkBinding

class WorkActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityWorkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWorkBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.doWorkBtn.setOnClickListener {
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .build()
            WorkManager.getInstance(this).enqueue(request)
        }
    }
}