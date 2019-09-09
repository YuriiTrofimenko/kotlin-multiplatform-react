package org.tyaa.kotlin.ankoexperiments.data.post

import io.ktor.client.HttpClient
//import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.host
import io.ktor.client.request.port
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.coroutines.withContext
// import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

class PostClient(private val coroutineContext: CoroutineContext) {

    private val client = HttpClient(CIO) {
        /*install(JsonFeature) {
            serializer = GsonSerializer()
            acceptContentTypes = listOf(ContentType.Application.Json)
        }*/
        defaultRequest { // this: HttpRequestBuilder ->
            method = HttpMethod.Get
            host = "10.0.2.2"
            port = 8080
        }
    }

    suspend fun getPosts(): String {
        return withContext(coroutineContext) {
            client.get<String>("/api/getPosts")
        }
    }

    suspend fun getPost(id: Int): String {
        return withContext(coroutineContext) {
            client.get<String>("/api/getPost?id=$id")
        }
    }

    /*suspend fun getPosts(): List<Post> {
        return withContext(coroutineContext) {
            client.get<List<Post>>("/api/getPosts")
        }
    }*/

    suspend fun getData(): String {
        return withContext(coroutineContext) {
            client.get<String>("http://www.metaweather.com/api/location/2487956/")
        }
    }
}