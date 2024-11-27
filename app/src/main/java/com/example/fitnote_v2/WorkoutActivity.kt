package com.example.fitnote_v2

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
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.data.Set
import com.example.fitnote_v2.data.WorkoutProgram
//import com.example.fitnote_v2.ui.screens.Workout
import com.example.fitnote_v2.ui.screens.Workout.Workout
import com.example.fitnote_v2.ui.theme.Fitnote_v2Theme

class WorkoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fitnote_v2Theme {
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
                                Set(repCount = 6, weight = 185, rest = 150)
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
                        sampleWorkout,
                        Modifier.padding(appPadding),
                        onCreateExercise = { sampleWorkout.exercises += it })
                }
            }
        }
    }
}