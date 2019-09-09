package org.tyaa.kotlin.ankoexperiments.ui.post

import androidx.lifecycle.ViewModel
import org.tyaa.kotlin.ankoexperiments.data.post.Post
import org.tyaa.kotlin.ankoexperiments.data.post.PostRepository

// QuoteRepository dependency will again be passed in the
// constructor using dependency injection
class PostViewModel(private val quoteRepository: PostRepository)
    : ViewModel() {

    fun getPosts() = quoteRepository.getPosts()

    fun addPost(quote: Post) = quoteRepository.addPost(quote)
}