package view

import materialui.components.card.card


import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import materialui.components.cardcontent.cardContent
import materialui.components.cardheader.cardHeader
import model.PostWithComments
import model.User
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import view.button.spinnerButtonView

object PostStyles : StyleSheet("PostStyles", isStatic = true) {
    val noComments by css {
        marginBottom = 8.px
    }

    val body by css {
        +noComments

        paddingBottom = 8.px
        borderBottom(1.px, BorderStyle.solid, Color("#000").withAlpha(0.1))
    }

    val comment by css {
        +body

        lastOfType {
            borderBottomStyle = BorderStyle.none
        }
    }
}

interface PostProps: RProps {
    var postWithComments: PostWithComments
    var user: User?
    var onMoreComments: () -> Unit
}

class PostState: RState {
    var noMore: Boolean = false
    var loading: Boolean = false
}

class PostView: RComponent<PostProps, PostState>() {
    private val post
        get() = props.postWithComments.post

    private val comments
        get() = props.postWithComments.comments

    init {
        state = PostState()
    }

    override fun componentDidUpdate(prevProps: PostProps, prevState: PostState, snapshot: Any) {
        println("componentDidUpdate")
        println("props.postWithComments.hasMore " + props.postWithComments.hasMore)
        /* if (state.loading && prevProps != props) {
            setState {
                noMore = prevProps.postWithComments.comments.size == props.postWithComments.comments.size
                println("prev = " + prevProps.postWithComments.comments.size)
                println("now = " + props.postWithComments.comments.size)
                loading = false
            }
        } */
        if (prevProps != props) {
            setState {
                noMore = !props.postWithComments.hasMore
                loading = false
            }
        }
    }

    override fun RBuilder.render() {
        card {
            cardHeader {
                attrs {
                    title {
                        +post.title
                    }
                }
            }
            cardContent {
                props.user?.let {
                    userView(it) {

                    }
                    // UserView.render(it, this)
                }
                styledDiv {
                    css {
                        if (comments.isNotEmpty()) {
                            +PostStyles.body
                        }
                        else {
                            +PostStyles.noComments
                        }
                    }
                    +post.body
                }
                comments.forEach {
                    commentView(it) {
                        css {
                            +PostStyles.comment
                        }
                    }
                }

                if (!state.noMore) {
                    spinnerButtonView(fetchData = {
                        setState {
                            loading = true
                        }
                        props.onMoreComments()
                    })
                }
            }
        }
    }
}

fun RBuilder.postView(post: PostWithComments, user: User? = null, onMoreComments: () -> Unit, handler: RHandler<PostProps> = {}) {
    child(PostView::class) {
        attrs.postWithComments = post
        attrs.user = user
        attrs.onMoreComments = onMoreComments
        handler()
    }
}