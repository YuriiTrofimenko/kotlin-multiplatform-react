package org.tyaa.kotlin.ankoexperiments.data.post

// FakePostDao must be passed in - it is a dependency
// You could also instantiate the DAO right inside the class without all the fuss, right?
// No. This would break testability - you need to be able to pass a mock version of a DAO
// to the repository (e.g. one that upon calling getPosts() returns a dummy list of quotes for testing)
// This is the core idea behind DEPENDENCY INJECTION - making things completely modular and independent.
class PostRepository private constructor(private val quoteDao: FakePostDao) {

    // This may seem redundant.
    // Imagine a code which also updates and checks the backend.
    fun addPost(quote: Post) {
        quoteDao.addPost(quote)
    }

    fun getPosts() = quoteDao.getPosts()

    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: PostRepository? = null

        fun getInstance(quoteDao: FakePostDao) =
            instance ?: synchronized(this) {
                instance
                    ?: PostRepository(quoteDao).also { instance = it }
            }
    }
}