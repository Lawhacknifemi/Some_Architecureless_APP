package com.example.blingytest

import android.content.Context
import android.text.Layout
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView

class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val isVisible = true

    private var onItemClickListener: ((View) -> Unit)? = null
    fun setOnItemClickListener(listener: (View) -> Unit) {
        onItemClickListener = listener
    }


    val name = itemView.findViewById<TextView>(R.id.textViewName)
    val location = itemView.findViewById<TextView>(R.id.textViewLocation)
    val detailTV = itemView.findViewById<TextView>(R.id.detailsTv)
    val profileImg = itemView.findViewById<ImageView>(R.id.profile_image)
    val detailTVLayout = itemView.findViewById<ConstraintLayout>(R.id.detailsTvLayout)





}


