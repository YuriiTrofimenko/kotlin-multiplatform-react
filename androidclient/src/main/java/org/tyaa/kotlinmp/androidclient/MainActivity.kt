package org.tyaa.kotlinmp.androidclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tyaa.kotlinmp.androidclient.network.CommentClient
import org.tyaa.kotlinmp.androidclient.network.PostClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.tyaa.kotlinmp.androidclient.model.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         GlobalScope.launch(Dispatchers.IO) {
            /*PostClient(coroutineContext).getPosts().forEach {
                Log.i("MyTag", it.title)
            }*/
             val postsString = PostClient(coroutineContext).getPosts()
             Log.i("MyTag", postsString)
             val posts = Json.parse(Post.serializer().list, postsString)
             posts.forEach {
                 Log.i("MyTag", it.title)
             }
        }
    }
}
