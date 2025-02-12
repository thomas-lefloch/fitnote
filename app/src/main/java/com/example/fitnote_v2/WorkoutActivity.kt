package com.example.fitnote_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.repository.WorkoutRepository
import com.example.fitnote_v2.ui.screens.Workout.Workout
import com.example.fitnote_v2.ui.theme.Fitnote_v2Theme

class WorkoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutIndex = intent.getIntExtra("workoutIndex", -1)
        if (workoutIndex == -1) throw IndexOutOfBoundsException()

        enableEdgeToEdge()
        setContent {
            Fitnote_v2Theme {

                val currentWorkout = WorkoutRepository.getWorkoutByIndex(workoutIndex)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ) { innerPadding ->

                    val appPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )

                    Workout(
                        currentWorkout,
                        Modifier.padding(appPadding),
                        onCreateExercise = {
                            WorkoutRepository.addExerciseToWorkout(currentWorkout, it)
                        },
                        onCreateSet = WorkoutRepository::addSetToExercise,
                        onEditSet = WorkoutRepository::editSet,
                    )
                }
            }
        }
    }
}