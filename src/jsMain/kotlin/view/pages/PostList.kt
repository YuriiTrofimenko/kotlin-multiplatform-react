package view.pages

import materialui.styles.createMuiTheme
import materialui.styles.muitheme.MuiTheme
import materialui.styles.muitheme.options.palette
import materialui.styles.muithemeprovider.muiThemeProvider
import materialui.styles.palette.options.main
import materialui.styles.palette.options.primary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.dom.*
import model.PostWithComments
import model.User
import react.*
import services.CommentsService
import services.PostWithCommentsService
import services.UserService
import styled.*
import view.postView
import kotlin.random.Random

private object PostListStyles: StyleSheet("PostListStyles", isStatic = true) {
    val wrapper by css {
        padding(32.px, 16.px)
    }

    val post by css {
        marginBottom = 32.px
    }
}

interface PostListProps: RProps {
    var coroutineScope: CoroutineScope
}

class PostListState: RState {
    var postWithComments: List<PostWithComments> = emptyList()
    var users: Map<Int, User> = emptyMap()
}

class PostListComponent: RComponent<PostListProps, PostListState>() {
    init {
        state = PostListState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun componentDidMount() {
        val postWithCommentsService = PostWithCommentsService(coroutineContext)
        val userService = UserService(coroutineContext)

        props.coroutineScope.launch {
            val posts = postWithCommentsService.getPostsWithComments()
            setState {
                postWithComments += posts
            }
            // Parallel coroutines execution
            val userIds = posts.map { it.post.userId }.toSet()
            val users = userIds
                .map { async { userService.getUser(it) } }
                .awaitAll()
                .toSet()
                .associateBy { it.id }

            setState {
                this.users = users
            }
        }
    }

    override fun RBuilder.render() {
        muiThemeProvider(theme) {
            header {
                styledH1 {
                    css {
                        marginLeft = 48.px
                    }
                    a("/") {
                        styledImg(src = "http://resources.jetbrains.com/storage/products/intellij-idea/img/meta/intellij-idea_logo_300x300.png"){
                            css {
                                height = 64.px
                                width = 64.px
                            }
                        }
                    }
                    +" Hello KotlinMP Web!"
                }
            }
            styledDiv {
                css {
                    +PostListStyles.wrapper
                }
                state.postWithComments.map { postWithComments ->
                    styledDiv {
                        css {
                            +PostListStyles.post
                        }
                        postView(postWithComments, state.users[postWithComments.post.userId], onMoreComments = {
                            onMoreComment(postWithComments.post.id)
                        })
                    }
                }
            }
        }
    }

    private fun onMoreComment(postId: Int) {
        val commentsService = CommentsService(coroutineContext)
        val post = state.postWithComments.find { it.post.id == postId }

        if (post != null) {
            props.coroutineScope.launch {
                val randomMoreNumber = Random.nextInt(3)
                val comments = commentsService.getComments(postId.toString(), post.comments.size, randomMoreNumber)
                println(post.comments.size)
                println(randomMoreNumber)
                setState {
                    postWithComments = postWithComments.map {
                        if (it != post) it else PostWithComments(it.post, it.comments + comments, randomMoreNumber != 0)
                    }
                }
            }
        }
    }
    companion object {
        private val theme: MuiTheme = createMuiTheme {
            palette {
                primary {
                    main = Color("#2196f3")
                }
            }
        }
    }
}

fun RBuilder.postListView(coroutineScope: CoroutineScope, handler: RHandler<PostListProps> = {}) {
    child(PostListComponent::class) {
        attrs.coroutineScope = coroutineScope
        handler()
    }
}