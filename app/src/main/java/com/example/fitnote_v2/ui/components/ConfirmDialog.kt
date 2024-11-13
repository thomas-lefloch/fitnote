package com.example.fitnote_v2.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDialog(
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    confirm: String,
) {
    DefaultDialog(
        onConfirmRequest = onConfirmRequest,
        onDismissRequest = onDismissRequest,
        title = title,
        confirm = confirm,
        content = { Text(content) }
    )
}