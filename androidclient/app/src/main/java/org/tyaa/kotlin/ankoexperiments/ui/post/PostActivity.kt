package org.tyaa.kotlin.ankoexperiments.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import org.tyaa.kotlin.ankoexperiments.data.post.Post
import org.tyaa.kotlin.ankoexperiments.data.post.PostClient
import org.tyaa.kotlin.ankoexperiments.utils.InjectorUtils

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeUi()
    }

    private fun initializeUi() {
        // Get the PostsViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        // Use ViewModelProviders class to create / get already created PostsViewModel
        // for this view (activity)
        val viewModel = ViewModelProviders.of(this, factory)
            .get(PostViewModel::class.java)

        val adapter = RecyclerViewAdapter(this, viewModel.getPosts().value) {
            startActivity<PostDetailsActivity>("postId" to it.id)
        }
        // Set adapter
        val layout = PostActivityView(adapter)
        // Set layout
        layout.setContentView(this)
        // Debug Observing LiveData from the PostsViewModel which in turn observes
        // LiveData from the repository, which observes LiveData from the DAO
        /*viewModel.getPosts().observe(this, Observer { posts ->
            posts.forEach { post ->
                Log.d("MyTag", post.title)
            }
        })*/
        /*// Add two posts to Post ViewModel
        val post = Post(1, "t1", "Lorem Ipsum 1", 1)
        viewModel.addPost(post)
        val post2 = Post(2, "t2", "Lorem Ipsum 2", 2)
        viewModel.addPost(post2)*/
        // Add posts from server module to Post ViewModel

        var postsString = ""
        runBlocking {
            GlobalScope.launch(Dispatchers.IO) {
                postsString = PostClient(coroutineContext).getPosts()
            }.join()
        }
        val posts = Json.parse(Post.serializer().list, postsString)
        posts.forEach {
            viewModel.addPost(it)
        }
    }
}
