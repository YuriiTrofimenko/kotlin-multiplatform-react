// import kotlinext.js.invoke // deprecated?
import kotlinx.css.*
import styled.StyledComponents
import styled.injectGlobal

object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = false).apply {
            body {
                margin(0.px)
                padding(0.px)
            }
        }

        StyledComponents.injectGlobal(styles.toString())
    }
}