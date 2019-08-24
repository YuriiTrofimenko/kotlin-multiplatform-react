package view.button

import react.RProps

interface SpinnerButtonProps : RProps {
    val classes: dynamic

    val rootStyle: String
        get() = classes["root"] as String

    var loader: Boolean
    var fetchData: () -> Unit
}