package view.button

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.icon.icon
import react.*
import react.dom.div
import react.dom.span
import styled.css
import styled.styledDiv

class SpinnerButtonState: RState {
    var loading: Boolean = false
}

class SpinnerButtonView : RComponent<SpinnerButtonProps, SpinnerButtonState>() {

    private fun fetchData () {
        setState {
            loading = true
            props.fetchData()
        }
    }

    override fun RBuilder.render() {
        div(props.rootStyle) {
            styledDiv {
                css {
                    marginTop = 60.px
                }

            }
            button {
                attrs {
                    disabled = state.loading
                    onClickFunction = {
                        fetchData()
                    }
                }
                if (state.loading){
                    icon{
                        attrs {
                            className = "fa fa-refresh fa-spin"
                        }
                    }
                    span {
                        +"Loading Data from Server"
                    }
                } else {
                    span {
                        +"Fetch Data from Server"
                    }
                }
            }
        }
    }
}

fun RBuilder.spinnerButtonView(fetchData: () -> Unit, handler: RHandler<SpinnerButtonProps> = {}) {
    child(SpinnerButtonView::class) {
        attrs.fetchData = fetchData
        handler()
    }
}