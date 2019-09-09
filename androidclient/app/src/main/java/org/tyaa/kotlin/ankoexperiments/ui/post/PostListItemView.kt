package org.tyaa.kotlin.ankoexperiments.ui.post

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.tyaa.kotlin.ankoexperiments.ui.observables.StandardObservableProperty
import org.tyaa.kotlin.ankoexperiments.ui.viewextensions.bindString

class PostListItemView: AnkoComponent<ViewGroup> {

    val titleData = StandardObservableProperty("")
    val imageData = StandardObservableProperty("")

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        verticalLayout {
            this.orientation = LinearLayout.HORIZONTAL
            lparams(matchParent, wrapContent)
            padding = dip(16)

            imageView {
                bindString(imageData)
            }.lparams {
                height = dip(40)
                width = dip(40)
                gravity = Gravity.CENTER
            }

            textView {
                bindString(titleData)
                textSize = 16f
            }.lparams {
                gravity = Gravity.CENTER
                margin = dip(10)
            }
        }
    }
}