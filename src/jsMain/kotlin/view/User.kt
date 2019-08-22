package view

import materialui.components.card.card

import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.html.style
import materialui.components.avatar.avatar
import materialui.components.avatar.enum.AvatarStyle
import materialui.components.cardcontent.cardContent
import materialui.components.cardheader.cardHeader
import materialui.components.cssbaseline.cssBaseline
import materialui.components.typography.enums.TypographyColor
import materialui.components.typography.enums.TypographyVariant
import materialui.components.typography.typography
import materialui.styles.StylesSet
import materialui.styles.childWithStyles
import model.Post
import model.User
import model.UserCardModel
import react.*
import react.dom.div
import react.dom.label
import react.dom.p
import styled.StyleSheet
import styled.css
import styled.inlineStyles
import styled.styledDiv

private fun User.toUserCardModel() = UserCardModel(
    name = this.name,
    login = this.username,
    avatarUrl = "http://i.pravatar.cc/56?u=${this.username}",
    email = this.email
)

private val userStyles: StylesSet.() -> Unit = {
    "@global" {
        ".bigAvatar" {
            margin(10.px)
            width = 60.px
            height = 60.px
        }
    }
}

interface UserProps: RProps {
    var user: User
}

class UserState: RState

class UserView: RComponent<UserProps, UserState>() {
    private val user
        get() = props.user.toUserCardModel()

    init {
        state = UserState()
    }

    override fun RBuilder.render() {
        card {
            cardHeader {
                attrs {
                    avatar {
                        avatar {
                            src = user.avatarUrl
                            classes(rootStyle = "bigAvatar")
                        }
                    }
                    title { +user.login }
                }
            }
            cardContent {
                typography {
                    attrs {
                        variant = TypographyVariant.body2
                        color = TypographyColor.textSecondary
                    }
                    +user.name; +" ("; +user.email; +")"
                }
            }
        }
    }
}

fun RBuilder.userView(user: User, handler: RHandler<UserProps> = {}) {
    childWithStyles(UserView::class, userStyles) {
        attrs.user = user
        handler()
    }
}