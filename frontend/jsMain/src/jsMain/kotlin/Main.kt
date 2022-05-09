import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.bromles.common.getPlatformName
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    var text by mutableStateOf("Hello, World!")
    val platformName = getPlatformName()

    renderComposable(rootElementId = "root") {
        Button(attrs = {
            onClick { text = "Hello, $platformName" }
        }) {
            Text(text)
        }
    }
}