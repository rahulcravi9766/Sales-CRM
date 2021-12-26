package com.rahul.adapter

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton

@BindingAdapter("android:visibilities")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.INVISIBLE else View.VISIBLE
}
