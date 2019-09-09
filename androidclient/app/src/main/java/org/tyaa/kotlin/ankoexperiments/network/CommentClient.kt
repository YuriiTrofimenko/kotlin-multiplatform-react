package org.tyaa.kotlin.ankoexperiments.data.post

import io.ktor.client.HttpClient
//import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import org.tyaa.kotlinmp.androidclient.JSON_PLACEHOLDER_URL
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class CommentClient constructor(coroutineContext: CoroutineContext) {
    private val client = HttpClient(CIO)

    suspend fun getComments(postId: String): String {
        return withContext(coroutineContext) {
            client.get<String>("$JSON_PLACEHOLDER_URL/posts/$postId/comments")
        }
    }
}