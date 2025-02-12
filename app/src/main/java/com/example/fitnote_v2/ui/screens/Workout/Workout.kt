package com.example.fitnote_v2.ui.screens.Workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.model.Exercise
import com.example.fitnote_v2.model.Set
import com.example.fitnote_v2.model.WorkoutProgram
import com.example.fitnote_v2.repository.WorkoutRepository


@Composable
fun Workout(
    workout: WorkoutProgram,
    modifier: Modifier = Modifier,
    onCreateExercise: (Exercise) -> Boolean,
    onCreateSet: (exercise: Exercise, new: Set) -> Boolean,
    onEditSet: (exercise: Exercise, old: Set, new: Set) -> Boolean,
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
            if (workout.exercises.isEmpty()) {
                item {
                    Column(
                        Modifier.fillParentMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.no_exercises), textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(workout.exercises.size) { i ->
                    ExerciseCard(
                        exercise = workout.exercises[i],
                        onEditSet = { old, new -> onEditSet(workout.exercises[i], old, new) },
                        onCreateSet = {
                            val newSet: Set
                            if (workout.exercises[i].sets.size <= 0) {
                                val goal = workout.exercises[i].goal
                                newSet = Set(
                                    repCount = goal.repMax,
                                    rest =  goal.rest,
                                    weight = goal.weight
                                )
                            } else {
                                newSet = workout.exercises[i].sets.last().copy(completedAt = null)
                            }
                            onCreateSet(workout.exercises[i], newSet)
                        },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        TextButton(
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

    var sampleWorkout = WorkoutRepository.getWorkouts()[0].copy()

    MaterialTheme {
        Workout(
            workout = sampleWorkout,
            onCreateExercise = {
                val newWorkout = sampleWorkout.copy()
                newWorkout.exercises += it
                sampleWorkout = newWorkout
                true
            },
            onCreateSet = {e, s -> true },
            onEditSet = {e, old, new -> true },
        )
    }
}