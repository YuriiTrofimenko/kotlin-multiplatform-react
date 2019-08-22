package view

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
import model.Post
import model.User
import react.*
import services.PostService
import services.UserService
import styled.*

private object ApplicationStyles: StyleSheet("ApplicationStyles", isStatic = true) {
    val wrapper by css {
        padding(32.px, 16.px)
    }

    val post by css {
        marginBottom = 32.px
    }
}

interface ApplicationProps: RProps {
    var coroutineScope: CoroutineScope
}

class ApplicationState: RState {
    var posts: List<Post> = emptyList()
    var users: Map<Int, User> = emptyMap()
}

class ApplicationComponent: RComponent<ApplicationProps, ApplicationState>() {
    init {
        state = ApplicationState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun componentDidMount() {
        val postService = PostService(coroutineContext)
        val userService = UserService(coroutineContext)

        props.coroutineScope.launch {
            val posts = postService.getPosts()
            setState {
                this.posts += posts
            }
            // Parallel coroutines execution
            val userIds = posts.map { it.userId }.toSet()
            val users = userIds
                .map { async { userService.getUser(it) } }
                .awaitAll()
                .toSet()

            setState {
                this.users = users.associateBy { it.id }
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
                    +ApplicationStyles.wrapper
                }
                state.posts.map { post ->
                    styledDiv {
                        css {
                            +ApplicationStyles.post
                        }
                        postView(post, state.users[post.userId])
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