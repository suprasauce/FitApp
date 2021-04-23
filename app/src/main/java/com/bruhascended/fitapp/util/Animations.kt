package com.bruhascended.fitapp.util

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat


enum class AnimationDuration (
        val ms: Long
) {
    LONG(700),
    MEDIUM(500),
    SHORT(350)
}

fun ImageView.animateTintColor (
        dest: Int, duration: AnimationDuration = AnimationDuration.SHORT
): View {
    val color = ImageViewCompat.getImageTintList(this)!!.defaultColor
    ValueAnimator.ofArgb(color, dest).apply {
        addUpdateListener {
            imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
        }
        setDuration(duration.ms)
        start()
    }
    return this
}

fun View.animateRotation (
        degrees: Float, duration: AnimationDuration = AnimationDuration.SHORT
): View {
    animate()
        .setDuration(duration.ms)
        .rotation(degrees)
        .setListener(null)
        .start()
    return this
}

fun View.animateFadeIn (
        alp: Float, duration: AnimationDuration = AnimationDuration.SHORT
): View {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .setDuration(duration.ms)
        .alpha(alp)
        .setListener(null)
        .start()
    return this
}

fun View.animateFadeOut (
    duration: AnimationDuration = AnimationDuration.SHORT
): View {
    animate()
        .setDuration(duration.ms)
        .alpha(0f)
        .setListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        .start()
    return this
}

fun View.animateFadeUpIn (
    translation: Float, duration: AnimationDuration = AnimationDuration.SHORT
): View {
    alpha = 0f
    translationY = translation
    visibility = View.VISIBLE
    animate()
        .setDuration(duration.ms)
        .alpha(1f)
        .translationY(0f)
        .setListener(null)
        .start()
    return this
}

fun View.animateFadeDownOut (
        translation: Float, duration: AnimationDuration = AnimationDuration.SHORT
): View {
    translationY = 0f
    animate()
        .setDuration(duration.ms)
        .alpha(0f)
        .translationY(-translation)
        .setListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        .start()
    return this
}