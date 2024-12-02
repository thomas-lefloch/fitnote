package com.example.fitnote_v2.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun <T : Number> IncrementalControls(
    value: T,
    onValueChange: (T) -> Unit,
    increments: List<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    ) {
    var controlsShown by remember { mutableStateOf(false) }
    Box(modifier, Alignment.Center) {
        TextButton(onClick = { controlsShown = true }, modifier.fillMaxWidth(), enabled = enabled) {
            Text(value.toString(), textAlign = TextAlign.Center)
        }
        DropdownMenu(expanded = controlsShown, onDismissRequest = { controlsShown = false }) {
            increments.forEach { increment ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { onValueChange(subtractGeneric(value, increment)) }) {
                        Text("-")
                    }
                    Text(increment.toString())
                    TextButton(onClick = { onValueChange(addGeneric(value, increment)) }) {
                        Text("+")
                    }
                }
            }
        }
    }
}

private fun <T : Number> addGeneric(a: T, b: T): T = when (a) {
    is Int -> (a.toInt() + b.toInt()) as T
    is Float -> (a.toFloat() + b.toFloat()) as T
    else -> throw IllegalArgumentException("Unsupported number type")
}

private fun <T : Number> subtractGeneric(a: T, b: T): T = when (a) {
    is Int -> (a.toInt() - b.toInt()) as T
    is Float -> (a.toFloat() - b.toFloat()) as T
    else -> throw IllegalArgumentException("Unsupported number type")
}
