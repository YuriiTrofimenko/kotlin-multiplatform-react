package org.tyaa.kotlin.ankoexperiments.ui.post

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class PostActivityView(var _adapter: RecyclerViewAdapter): AnkoComponent<PostActivity> {

    override fun createView(ui: AnkoContext<PostActivity>): View = with(ui) {

        verticalLayout {
            lparams(matchParent, wrapContent)

            recyclerView {
                layoutManager = LinearLayoutManager(context)
                adapter = _adapter
                backgroundColor = Color.parseColor("#F8F2F2")
            }
        }
    }
}