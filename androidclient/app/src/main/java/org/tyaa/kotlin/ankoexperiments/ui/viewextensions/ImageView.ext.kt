package org.tyaa.kotlin.ankoexperiments.ui.viewextensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import org.tyaa.kotlin.ankoexperiments.ui.observables.StandardObservableProperty

@Suppress("NOTHING_TO_INLINE")
inline fun ImageView.bindString(bond: StandardObservableProperty<String>) {
    bond.addValueChangeListener {
        Glide.with(this).load("http://i.pravatar.cc/56?u=$it").into(this)
    }
}