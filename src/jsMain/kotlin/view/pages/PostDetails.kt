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
import model.*
import react.dom.*
import react.*
import services.CommentsService
import services.PostWithCommentsService
import services.UserService
import styled.*
import view.postView
import kotlin.random.Random

private object PostDetailsStyles: StyleSheet("PostDetailsStyles", isStatic = true) {
    val wrapper by css {
        padding(32.px, 16.px)
    }

    val post by css {
        marginBottom = 32.px
    }
}

interface PostDetailsProps: RProps {
    var coroutineScope: CoroutineScope
    var postId: Int
}

class PostDetailsState: RState {
    var postWithComments: PostWithComments = PostWithComments(post = Post(0, "", "", 0), comments = emptyList(), hasMore = false)
    var user: User = User(0, "", "", "", UserAddress("", "", "", "", Geo("", "")), "", "", UserCompany("", "", ""))
}

class PostDetailsComponent: RComponent<PostDetailsProps, PostDetailsState>() {
    init {
        state = PostDetailsState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun componentDidMount() {
        val postWithCommentsService = PostWithCommentsService(coroutineContext)
        val userService = UserService(coroutineContext)

        props.coroutineScope.launch {
            val post = postWithCommentsService.getPostWithComments(props.postId.toString())
            setState {
                postWithComments = post
            }
            val user = userService.getUser(post.post.userId)
            setState {
                this.user = user
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
                    +" Post Details"
                }
            }
            styledDiv {
                css {
                    +PostDetailsStyles.wrapper
                }
                styledDiv {
                    css {
                        +PostDetailsStyles.post
                    }
                    postView(state.postWithComments, state.user, hideMoreCommentsButton = true)
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

fun RBuilder.postDetailsView(coroutineScope: CoroutineScope, id: Int, handler: RHandler<PostDetailsProps> = {}) {
    child(PostDetailsComponent::class) {
        attrs.coroutineScope = coroutineScope
        attrs.postId = id
        handler()
    }
}