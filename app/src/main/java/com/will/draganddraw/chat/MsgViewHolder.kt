package com.will.draganddraw.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.will.draganddraw.R

// 密封类，解决 when 判断语句中 else 的情况
sealed class MsgViewHolder(view: View): RecyclerView.ViewHolder(view) {

    class LeftViewHolder(view: View): MsgViewHolder(view) {
        val leftMsg: TextView = view.findViewById(R.id.leftMsg)
    }
    class RightViewHolder(view: View): MsgViewHolder(view) {
        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
    }

}