package com.example.fitnote_v2

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.data.Range
import com.example.fitnote_v2.data.Set
import com.example.fitnote_v2.data.WorkoutProgram
import com.example.fitnote_v2.ui.screens.Workout
import com.example.fitnote_v2.ui.theme.Fitnote_v2Theme

class WorkoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fitnote_v2Theme {
                val testWorkout: WorkoutProgram = WorkoutProgram(
                    name = "Full Body Strength",
                    description = "A comprehensive full body workout focusing on compound movements",
                    exercises = listOf(
                        Exercise(
                            name = "Barbell Squat",
                            description = "Traditional back squat with barbell",
                            setCount = 3,
                            goal = Goal(
                                repCount = Range(8, 12),
                                weight = 100,
                                rest = 120 // seconds
                            ),
                            sets = listOf(
                                Set(repCount = 10, weight = 100, rest = 120),
                                Set(repCount = 9, weight = 100, rest = 120),
                                Set(repCount = 8, weight = 100, rest = 120)
                            )
                        ),
                        Exercise(
                            name = "Bench Press",
                            description = "Flat barbell bench press for chest development",
                            setCount = 3,
                            goal = Goal(
                                repCount = Range(6, 10),
                                weight = 80,
                                rest = 90
                            ),
                            sets = listOf(
                                Set(repCount = 8, weight = 80, rest = 90),
                                Set(repCount = 7, weight = 80, rest = 90),
                                Set(repCount = 6, weight = 80, rest = 90)
                            )
                        ),
                        Exercise(
                            name = "Bent Over Row",
                            description = "Barbell row for back strength and muscle development",
                            setCount = 3,
                            goal = Goal(
                                repCount = Range(8, 12),
                                weight = 70,
                                rest = 90
                            ),
                            sets = listOf(
                                Set(repCount = 12, weight = 70, rest = 90),
                                Set(repCount = 10, weight = 70, rest = 90),
                                Set(repCount = 9, weight = 70, rest = 90)
                            )
                        )
                    )
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Workout(testWorkout, Modifier.padding(innerPadding))
                }
            }
        }
    }
}