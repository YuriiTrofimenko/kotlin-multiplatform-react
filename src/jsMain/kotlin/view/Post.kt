package view

import materialui.components.card.card

import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import materialui.components.cardcontent.cardContent
import materialui.components.cardheader.cardHeader
import model.Post
import model.User
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

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
    var post: Post
    var user: User?
}

class PostState: RState

class PostView: RComponent<PostProps, PostState>() {
    private val post
        get() = props.post

    init {
        state = PostState()
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
                styledDiv {
                    css {
                        +PostStyles.noComments
                    }
                    +post.body
                }
            }
        }
    }
}

fun RBuilder.postView(post: Post, user: User? = null, handler: RHandler<PostProps> = {}) {
    child(PostView::class) {
        attrs.post = post
        attrs.user = user
        handler()
    }
}