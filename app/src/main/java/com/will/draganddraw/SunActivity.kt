package com.will.draganddraw

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SunActivity: AppCompatActivity() {
    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }

    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }

    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    private var isSunDown = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sun)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)

        sceneView.setOnClickListener {
            if (isSunDown) {
                sunupAnimator()
                isSunDown = false
            } else {
                startAnimation()
                isSunDown = true
            }

        }
    }

    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator.ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)

        val scaleXAnimator = ObjectAnimator.ofFloat(sunView, View.SCALE_X, 1f, 1.5f)
            .setDuration(1000)
        val scaleYAnimator = ObjectAnimator.ofFloat(sunView, View.SCALE_Y, 1f, 1.5f)
            .setDuration(1000)
        scaleXAnimator.repeatCount = -1
        scaleXAnimator.repeatMode = ValueAnimator.REVERSE
        scaleYAnimator.repeatCount = -1
        scaleYAnimator.repeatMode = ValueAnimator.REVERSE


        heightAnimator.interpolator = AccelerateInterpolator()

        heightAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                Log.e("WillWolf", "onAnimationEnd-->")
                scaleXAnimator.cancel()
                scaleYAnimator.cancel()
                if (sunView.scaleX != 1f) {
                    scaleXAnimator.repeatCount = 0
                    scaleXAnimator.reverse()
                }
                if (sunView.scaleY != 1f) {
                    scaleYAnimator.repeatCount = 0
                    scaleYAnimator.reverse()
                }
            }
        })

        val sunsetSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())


        // AnimatorSet 是可以放在一起执行的动画集
        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .with(scaleXAnimator)
            .with(scaleYAnimator)
            .before(nightSkyAnimator)

        animatorSet.start()
    }

    private fun sunupAnimator() {
        val sunYStart = skyView.height.toFloat()
        val sunYEnd = sunView.top.toFloat()


        val heightAnimator = ObjectAnimator.ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)

        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor,)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .after(nightSkyAnimator)
        animatorSet.start()
    }
}