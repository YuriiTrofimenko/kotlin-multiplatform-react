import kotlin.browser.document
// hello world kotlin-js
fun main() {
    val message = "Kotlin multiplatform react application demo"
    document.getElementById("js-response")?.textContent = message
}                