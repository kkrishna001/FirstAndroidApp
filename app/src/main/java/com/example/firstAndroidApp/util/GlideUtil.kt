package com.example.firstAndroidApp.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

open class GlideUtil {

    fun setImage(context: Context,poster:String,view: ImageView){
        Glide.with(context).load(poster).diskCacheStrategy(
            DiskCacheStrategy.ALL
        ).into(view)
    }
}