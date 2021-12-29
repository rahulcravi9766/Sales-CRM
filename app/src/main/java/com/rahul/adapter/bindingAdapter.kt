package com.rahul.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibilities")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.INVISIBLE else View.VISIBLE
}
