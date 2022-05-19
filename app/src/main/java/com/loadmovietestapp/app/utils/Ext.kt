package com.loadmovietestapp.app.utils

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.loadmovietestapp.app.R
import com.loadmovietestapp.app.TestApplication
import io.reactivex.disposables.Disposable


fun <T : Any?> LifecycleOwner.subscribe(liveData: LiveData<T>, action: (T) -> Unit) {
    liveData.observe(this, Observer<T> { t -> t?.run { action.invoke(this) } })
}

fun Disposable?.safeDispose() {
    this?.let {
        if (!this.isDisposed) {
            this.dispose()
        }
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.loadPoster(imageUrl: String) {
    GlideApp.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.ic_launcher_foreground)
        .transform(MultiTransformation(CenterCrop(), RoundedCorners(dpToPx(2))))
        .into(this)
}

fun hasNetwork(): Boolean {
    return checkConnectedToANetwork(TestApplication.instance)
}