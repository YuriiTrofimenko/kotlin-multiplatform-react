package org.tyaa.kotlin.ankoexperiments.ui.post

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext
import org.tyaa.kotlin.ankoexperiments.data.post.Post

class RecyclerViewAdapter(
    private val context: Context,
    private val posts: List<Post>?,
    private val listener: (Post) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postListItemView = PostListItemView()
        return ViewHolder(postListItemView, postListItemView.createView(AnkoContext.create(context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(posts!![position], listener)
    }

    override fun getItemCount(): Int = posts!!.size

    class ViewHolder(val postListItemView: PostListItemView, override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(item: Post, listener: (Post) -> Unit) {
            postListItemView.titleData.value = item.title
            postListItemView.imageData.value = item.title
            containerView.setOnClickListener { listener(item) }
        }
    }
}