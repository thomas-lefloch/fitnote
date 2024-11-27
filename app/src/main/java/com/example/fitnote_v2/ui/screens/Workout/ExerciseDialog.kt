package com.example.fitnote_v2.ui.screens.Workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.ui.components.DefaultDialog

@Composable
fun ExerciseDialog(
    onDismiss: () -> Unit,
    onConfirm: (Exercise) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    var repMin by remember { mutableStateOf("") }
    var repMax by remember { mutableStateOf("") }
    var setCount by remember { mutableStateOf("") }
    var rest by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var repMinError by remember { mutableStateOf(false) }
    var repMaxError by remember { mutableStateOf(false) }
    var setCountError by remember { mutableStateOf(false) }
    var restError by remember { mutableStateOf(false) }

    val isValidInput = name.isNotBlank() &&
            repMin.isNotBlank() && repMin.toIntOrNull() != null &&
            repMax.isNotBlank() && repMax.toIntOrNull() != null &&
            setCount.isNotBlank() && setCount.toIntOrNull() != null &&
            rest.isNotBlank() && rest.toIntOrNull() != null

    val confirmExercise: () -> Unit = {
        if (isValidInput) {
            val goal = Goal(
                repMin = repMin.toInt(),
                repMax = repMax.toInt(),
                setCount = setCount.toInt(),
                weight = weight.toIntOrNull() ?: 0, // TODO: proper support for bodyweight exercise
                rest = rest.toInt()
            )
            onConfirm(Exercise(name = name, note = note, goal = goal))
        }
    }

    DefaultDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = confirmExercise,
        title = stringResource(R.string.create_exercise_dialog_title),
        confirm = stringResource(R.string.create),
        isConfirmButtonEnabled = isValidInput
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.label_exercise_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )

            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(R.string.label_note)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )

            Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = repMin,
                    onValueChange = {
                        repMin = it
                        repMinError = it.isBlank() || it.toIntOrNull() == null
                    },
                    label = { Text(stringResource(R.string.label_minimum_reps)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    isError = repMinError,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                TextField(
                    value = repMax,
                    onValueChange = {
                        repMax = it
                        repMaxError = it.isBlank() || it.toIntOrNull() == null
                    },
                    label = { Text(stringResource(R.string.label_maximum_reps)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    isError = repMaxError,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = setCount,
                    onValueChange = {
                        setCount = it
                        setCountError = it.isBlank() || it.toIntOrNull() == null
                    },
                    label = { Text(stringResource(R.string.label_set_count)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    isError = setCountError,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(stringResource(R.string.label_weight)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                TextField(
                    value = rest,
                    onValueChange = {
                        rest = it
                        restError = it.isBlank() || it.toIntOrNull() == null
                    },
                    label = { Text(stringResource(R.string.label_rest_time)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { confirmExercise() }
                    ),
                    isError = restError,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }
    }
}