package services

import database.Posts
import database.database
import model.Post
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import rpc.RPCService
import kotlin.random.Random

actual class PostService: RPCService {
    actual suspend fun getPost(id: String): Post {
        return database {
            Posts.select { Posts.postId eq id.toInt() }.firstOrNull()?.let {
                Post(it[Posts.postId], it[Posts.title], it[Posts.body], it[Posts.userId])
            }
        } ?: throw IllegalArgumentException("no such post: $id")
    }

    actual suspend fun getPosts(): List<Post> {
        return database {
            Posts.selectAll()
                .map {
                    Post(it[Posts.postId], it[Posts.title], it[Posts.body], it[Posts.userId])
                }
                .shuffled() // Randomize feed
                .take(10) // Take 10 posts
        }
    }
}