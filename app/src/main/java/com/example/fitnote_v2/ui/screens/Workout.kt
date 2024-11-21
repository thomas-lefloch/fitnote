package com.example.fitnote_v2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.data.Set
import com.example.fitnote_v2.data.WorkoutProgram
import com.example.fitnote_v2.ui.components.DefaultDialog

@Composable
fun Workout(
    workout: WorkoutProgram,
    modifier: Modifier = Modifier,
    onCreateExercise: (Exercise) -> Unit
) {
    var showExerciseCreationDialog by remember { mutableStateOf(false) }

    Column(modifier.fillMaxWidth()) {
        Text(
            text = workout.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
//            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            items(workout.exercises.size) { i ->
                ExerciseCard(exercise = workout.exercises[i])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Button(
            onClick = { showExerciseCreationDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(R.string.button_add_exercise),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    if (showExerciseCreationDialog) {
        ExerciseCreationDialog(
            onDismiss = { showExerciseCreationDialog = false },
            onConfirm = { exercise ->
                onCreateExercise(exercise)
                showExerciseCreationDialog = false
            }
        )
    }

}

@Composable
fun ExerciseCard(
    exercise: Exercise, modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }

    Card(
        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {

            ExpandableCardHeader(
                exercise.name,
                exercise.note,
                stringResource(
                    R.string.goal,
                    exercise.goal.repMin,
                    exercise.goal.repMax,
                    exercise.goal.setCount,
                    exercise.goal.rest
                ),
                isExpanded,
                { isExpanded = !isExpanded },
                modifier.padding(bottom = 6.dp)
            )

            if (!isExpanded) return@Card

            HorizontalDivider(modifier = Modifier.padding(top = 8.dp), thickness = .5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TableTitle(stringResource(R.string.reps), modifier = Modifier.weight(1f))
                TableTitle(stringResource(R.string.weight), modifier = Modifier.weight(1f))
                TableTitle(stringResource(R.string.rest), modifier = Modifier.weight(1f))
                TableTitle("", modifier = Modifier.width(48.dp))
            }

            HorizontalDivider(thickness = .5.dp)

            exercise.sets.forEach { set ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${set.repCount}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "${set.weight}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center

                    )
                    Text(
                        "${set.rest}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {}, //TODO: implement on done
                        modifier = Modifier.width(48.dp) //TODO: fix magic value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.done),
                            tint = Color(0xFFA0B397), // TODO: put in theme
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TableTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ExpandableCardHeader(
    title: String,
    description: String,
    goal: String,
    expanded: Boolean,
    onExpandRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            IconButton(onClick = onExpandRequest) {
                Icon(
                    imageVector = when (expanded) {
                        true -> Icons.Default.KeyboardArrowDown
                        false -> Icons.AutoMirrored.Filled.KeyboardArrowLeft
                    },
                    contentDescription = when (expanded) {
                        true -> stringResource(R.string.expanded)
                        false -> stringResource(R.string.collapsed)
                    },
                )
            }
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(modifier = Modifier.padding(top = 4.dp)) {
            Text(
                text = "Goal: ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = goal,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ExerciseCreationDialog(
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

    // Validate inputs
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
                weight = weight.toIntOrNull()
                    ?: 0, // TODO: implement proper support for non weight exercise
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
            // Exercise Name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.label_exercise_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )

            // Optional Note
            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(R.string.label_note)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )

            // Goal Inputs
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

                // Optional Weight
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

@Preview(showBackground = true, widthDp = 540, heightDp = 800)
@Composable
fun WorkoutTrackerPreview() {
    // Sample Workout Program with Multiple Exercises

    var sampleWorkout = WorkoutProgram(
        id = "WP001",
        name = "Full Body Strength Training",
        description = "A comprehensive full-body strength training program focusing on major muscle groups",
        exercises = listOf(
            Exercise(
                id = "EX001",
                name = "Barbell Squats",
                note = "Focus on proper form and depth",
                goal = Goal(
                    repMin = 8,
                    repMax = 12,
                    setCount = 4,
                    weight = 135,
                    rest = 90
                ),
                sets = listOf(
                    Set(repCount = 10, weight = 95, rest = 90),
                    Set(repCount = 10, weight = 115, rest = 90),
                    Set(repCount = 8, weight = 135, rest = 120),
                )
            ),
            Exercise(
                id = "EX002",
                name = "Bench Press",
                note = "Maintain steady tempo and full range of motion",
                goal = Goal(
                    repMin = 6,
                    repMax = 10,
                    setCount = 4,
                    weight = 185,
                    rest = 120
                ),
                sets = listOf(
                    Set(repCount = 10, weight = 135, rest = 90),
                    Set(repCount = 8, weight = 155, rest = 120),
                    Set(repCount = 6, weight = 185, rest = 150),
                )
            ),
            Exercise(
                id = "EX003",
                name = "Deadlifts",
                note = "Engage core and maintain neutral spine",
                goal = Goal(
                    repMin = 5,
                    repMax = 8,
                    setCount = 3,
                    weight = 225,
                    rest = 180
                ),
                sets = listOf(
                    Set(repCount = 8, weight = 185, rest = 120),
                    Set(repCount = 6, weight = 225, rest = 180),
                    Set(repCount = 5, weight = 225, rest = 180)
                )
            ),
            Exercise(
                id = "EX004",
                name = "Pull-Ups",
                note = "Full extension at bottom, chin over bar at top",
                goal = Goal(
                    repMin = 6,
                    repMax = 10,
                    setCount = 3,
                    weight = 0,
                    rest = 90
                ),
                sets = listOf(
                    Set(repCount = 8, weight = 0, rest = 90),
                    Set(repCount = 7, weight = 0, rest = 90),
                    Set(repCount = 6, weight = 0, rest = 90)
                )
            )
        )
    )

    MaterialTheme {
        Workout(workout = sampleWorkout, onCreateExercise = {
            val newWorkout = sampleWorkout.copy()
            newWorkout.exercises += it
            sampleWorkout = newWorkout
        })
    }
}