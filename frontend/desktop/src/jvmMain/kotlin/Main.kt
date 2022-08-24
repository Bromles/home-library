import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.bromles.common.App
import com.bromles.common.WindowSize


fun main() = application {
    val windowState = rememberWindowState(size = DpSize(850.dp, 650.dp))

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
    ) {
        App(windowSize = WindowSize.basedOnWidth(windowState.size.width))
    }
}
