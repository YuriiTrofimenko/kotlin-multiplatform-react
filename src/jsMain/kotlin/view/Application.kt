package view

import view.header.Header

import kotlinx.css.Color

import materialui.styles.createMuiTheme
import materialui.styles.muitheme.MuiTheme
import materialui.styles.muitheme.options.palette
import materialui.styles.muithemeprovider.muiThemeProvider
import materialui.styles.palette.options.main
import materialui.styles.palette.options.primary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.css.marginBottom
import kotlinx.css.padding
import kotlinx.css.px
import react.dom.*
import model.Post
import react.*
import services.PostService
import styled.StyleSheet
import styled.css
import styled.styledDiv
import kotlin.random.Random

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
}

class ApplicationComponent: RComponent<ApplicationProps, ApplicationState>() {
    init {
        state = ApplicationState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun componentDidMount() {
        val postService = PostService(coroutineContext)

        props.coroutineScope.launch {
            val posts = postService.getPosts()
            setState {
                this.posts += posts
            }
        }
    }

    override fun RBuilder.render() {
        muiThemeProvider(theme) {
            header {
                Header.render(this)
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
                        postView(post)
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