package com.flesh.mediasplitter

import android.view.animation.LinearInterpolator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.Keyframe
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Created by aaronfleshner on 12/13/17.
 */
class RotatingCircleImageView : CircleImageView {

    private var rotationAnimator: ObjectAnimator? = null
    private var myRotation: Float = 0.toFloat()
    private var currentAnimationTime = 0.toLong()

    val animationRunning: Boolean
        @Synchronized get() = rotationAnimator != null && rotationAnimator!!.isRunning

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun setMyRotation(rotation: Float) {
        this.myRotation = rotation
        Log.d("CustomImage", "Rotation: " + rotation)
        invalidate()
    }

    @Synchronized
    fun startAnimation(animationDuration: Int, resetAnimation: Boolean = false ) {

        if (rotationAnimator == null || !rotationAnimator!!.isRunning) {
            val kf0 = Keyframe.ofFloat(0f, 0f)
            val kf2 = Keyframe.ofFloat(0.5f, 180f)
            val kf1 = Keyframe.ofFloat(1f, 360f)

            val pvhRotation = PropertyValuesHolder.ofKeyframe("myRotation", kf0, kf1, kf2)
            rotationAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhRotation)
            rotationAnimator!!.repeatCount = ObjectAnimator.INFINITE
            rotationAnimator!!.interpolator = LinearInterpolator()
            rotationAnimator!!.duration = animationDuration.toLong()
            rotationAnimator!!.start()
            rotationAnimator!!.currentPlayTime = if(resetAnimation) 0L else currentAnimationTime

        } else {
            // Already running
        }
    }

    @Synchronized
    fun stopAnimation() {
        if (rotationAnimator != null) {
            currentAnimationTime = rotationAnimator!!.currentPlayTime
            rotationAnimator!!.cancel()
            rotationAnimator = null
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.rotate(myRotation, getWidth() / 2.0f, getHeight() / 2.0f)
        super.onDraw(canvas)
    }
}