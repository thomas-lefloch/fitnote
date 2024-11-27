package com.example.fitnote_v2.ui.screens.Workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.data.Set
import com.example.fitnote_v2.data.WorkoutProgram


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
        ) {
            items(workout.exercises.size) { i ->
                ExerciseCard(exercise = workout.exercises[i])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        TextButton (
            onClick = { showExerciseCreationDialog = true },
            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                contentColor = MaterialTheme.colorScheme.onSurface,
//            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(R.string.button_add_exercise),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    if (showExerciseCreationDialog) {
        ExerciseDialog(
            onDismiss = { showExerciseCreationDialog = false },
            onConfirm = { exercise ->
                onCreateExercise(exercise)
                showExerciseCreationDialog = false
            }
        )
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