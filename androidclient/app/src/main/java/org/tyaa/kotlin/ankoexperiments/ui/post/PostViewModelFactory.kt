package org.tyaa.kotlin.ankoexperiments.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tyaa.kotlin.ankoexperiments.data.post.PostRepository

// The same repository that's needed for PostViewModel
// is also passed to the factory
class PostViewModelFactory(private val postRepository: PostRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(postRepository) as T
    }
}