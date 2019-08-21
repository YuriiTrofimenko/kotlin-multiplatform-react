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
import kotlinx.css.marginBottom
import kotlinx.css.padding
import kotlinx.css.px
import react.dom.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet

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

class ApplicationState: RState

class ApplicationComponent: RComponent<ApplicationProps, ApplicationState>() {
    init {
        state = ApplicationState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        muiThemeProvider(theme) {
            header {
                Header.render(this)
            }
            div {
                h2 {
                    +"Welcome to React with Kotlin"
                }
            }
            p {
                +"To get started, edit "
                code { +"app/App.kt" }
                +" and save to reload."
            }
            div {

            }
            div {
                AppbarsDemo.render(this)
            }
            div {
                ButtonsDemo.render(this)
            }
            div {
                InputAdornmentsDemo.render(this)
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