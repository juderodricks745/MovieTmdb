package com.davidbronn.movietmdb.utils.binding

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.utils.extensions.asVisibility
import com.davidbronn.movietmdb.utils.misc.Constants

/**
 * Created by Jude on 12/January/2020
 */
object ViewBindingUtils {

    private const val IMAGE_URL = "imageUrl"
    private const val IMAGE_TITLE = "imageTitle"
    private const val VIEW_VISIBILITY = "visibleGone"
    private const val IMAGE_BACKDROP = "imageBackDrop"

    @JvmStatic
    @BindingAdapter(IMAGE_BACKDROP)
    fun ImageView.setImageBackDropUrl(url: String?) {
        Glide.with(this)
            .load(Constants.Urls.POSTER_500 + url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }

    @JvmStatic
    @BindingAdapter(IMAGE_URL, IMAGE_TITLE)
    fun ImageView.setImagePosterUrlWithTitle(url: String?, titleTextView: AppCompatTextView? = null) {
        if (url.isNullOrBlank()) return
        if (titleTextView != null) {
            Glide.with(context)
                .asBitmap()
                .load(Constants.Urls.POSTER_200 + url)
                .apply(RequestOptions().centerCrop())
                .placeholder(R.drawable.img_movie_placeholder)
                .error(R.drawable.img_movie_placeholder)
                .into(object : BitmapImageViewTarget(this) {
                    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        super.onResourceReady(bitmap, transition)
                        Palette.from(bitmap).generate { palette ->
                            val color = palette?.getVibrantColor(
                                ContextCompat.getColor(
                                    this@setImagePosterUrlWithTitle.context,
                                    R.color.black_translucent_60
                                )
                            )
                            color?.let { titleTextView.setBackgroundColor(it) }
                        }
                    }
                })
        } else {
            Glide.with(context)
                .load(Constants.Urls.POSTER_200 + url)
                .apply(RequestOptions().centerCrop())
                .placeholder(R.drawable.img_movie_placeholder)
                .error(R.drawable.img_movie_placeholder)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter(VIEW_VISIBILITY)
    fun showHide(view: View, show: Boolean) {
        view.visibility = show.asVisibility()
    }
}