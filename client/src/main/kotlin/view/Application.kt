package view

import kotlinx.coroutines.CoroutineScope
import react.*
import react.dom.div
import react.dom.nav
import react.router.dom.*
import view.pages.PostListComponent
import view.pages.postDetailsView
import view.pages.postListView

interface ApplicationProps: RProps {
    var id: Int
    var coroutineScope: CoroutineScope
}

class ApplicationComponent : RComponent<ApplicationProps, RState>() {
    override fun RBuilder.render() {
        browserRouter {
            /* nav {
                navLink(to="/"){}
                navLink(to="/post/"){}
            } */
            switch {
                route<ApplicationProps>("/", exact = true) {
                    div {
                        postListView(coroutineScope = props.coroutineScope)
                    }
                }
                route<ApplicationProps>("/post/:id") {urlProps ->
                    div {
                        postDetailsView(coroutineScope = props.coroutineScope, id = urlProps.match.params.id)
                    }
                }
            }
        }
    }
}