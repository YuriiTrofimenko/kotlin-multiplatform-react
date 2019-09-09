package org.tyaa.kotlin.ankoexperiments.utils

import org.tyaa.kotlin.ankoexperiments.data.FakeDatabase
import org.tyaa.kotlin.ankoexperiments.data.post.PostRepository
import org.tyaa.kotlin.ankoexperiments.ui.post.PostViewModelFactory

// Finally a singleton which doesn't need anything passed to the constructor
object InjectorUtils {

    // This will be called from QuotesActivity
    fun provideQuotesViewModelFactory(): PostViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val quoteRepository = PostRepository.getInstance(FakeDatabase.getInstance().postDao)
        return PostViewModelFactory(quoteRepository)
    }
}