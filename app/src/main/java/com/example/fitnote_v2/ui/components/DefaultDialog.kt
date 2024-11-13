package com.example.fitnote_v2.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.fitnote_v2.R

@Composable
fun DefaultDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    title: String,
    confirm: String,
    isConfirmButtonEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = content,
        confirmButton = {
            Button(
                onClick = onConfirmRequest,
                enabled = isConfirmButtonEnabled,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(confirm, style=MaterialTheme.typography.titleSmall)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel), style=MaterialTheme.typography.titleSmall)
            }
        },
        shape = MaterialTheme.shapes.medium,
    )
}