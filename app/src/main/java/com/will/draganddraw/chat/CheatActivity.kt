package com.will.draganddraw.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.will.draganddraw.R

class CheatActivity : AppCompatActivity() {
    private val msgList = ArrayList<Msg>()
    private lateinit var adapter: MsgAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var etInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        initMsg()

        etInput = findViewById(R.id.inputText)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MsgAdapter(msgList)

//        ::adapter.isInitialized
        recyclerView.adapter = adapter
        findViewById<Button>(R.id.send).setOnClickListener {
            val content = etInput.text.toString()
            if (content.isNotEmpty()) {
                val msg = Msg(content, Msg.TYPE_SEND)
                msgList.add(msg)
                adapter.notifyItemInserted(msgList.size - 1)
                // 刷新 recyclerView 中的显示
                recyclerView.scrollToPosition(msgList.size - 1)
                etInput.setText("")
            }
        }
    }

    private fun initMsg() {
        val msg1 = Msg("Hello guy.", Msg.TYPE_RECEIVED)
        msgList.add(msg1)

        val msg2 = Msg("Hello. Who is that?", Msg.TYPE_SEND)
        msgList.add(msg2)

        val msg3 = Msg("This is Tom. Nice talking to you", Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}