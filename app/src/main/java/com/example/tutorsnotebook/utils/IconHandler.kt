package com.example.tutorsnotebook.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

class IconHandler {
    companion object {
        fun setColoredImage(context: Context, imageView: ImageView, imageId: Int, colorId: Int) {
            val vectorImage: Drawable =
                ResourcesCompat.getDrawable(context.resources, imageId, null)!!
            DrawableCompat.setTint(
                DrawableCompat.wrap(vectorImage),
                ContextCompat.getColor(context, colorId)
            )
            imageView.setImageDrawable(vectorImage)
        }

        fun getColoredDrawable(context: Context, imageId: Int, colorId: Int): Drawable {
            val vectorImage: Drawable =
                ResourcesCompat.getDrawable(context.resources, imageId, null)!!
            DrawableCompat.setTint(
                DrawableCompat.wrap(vectorImage),
                ContextCompat.getColor(context, colorId)
            )
            return vectorImage
        }
    }
}