package com.bromles.common

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun App(windowSize: WindowSize) {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    CustomTheme(
        windowSize = windowSize
    ) {
        Button(onClick = {
            text = "Hello, $platformName"
        }) {
            Text(text)
        }
    }
}
