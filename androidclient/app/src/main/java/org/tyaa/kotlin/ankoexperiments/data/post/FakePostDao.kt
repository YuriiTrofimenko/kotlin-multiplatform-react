package org.tyaa.kotlin.ankoexperiments.data.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakePostDao {
    // A fake database table
    private val postList = mutableListOf<Post>()
    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    private val posts = MutableLiveData<List<Post>>()

    init {
        // Immediately connect the now empty postList
        // to the MutableLiveData which can be observed
        posts.value = postList
    }

    fun addPost(post: Post) {
        postList.add(post)
        // After adding a post to the "database",
        // update the value of MutableLiveData
        // which will notify its active observers
        posts.value = postList
    }

    // Casting MutableLiveData to LiveData because its value
    // shouldn't be changed from other classes
    fun getPosts() = posts as LiveData<List<Post>>
}