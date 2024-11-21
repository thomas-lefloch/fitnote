package com.example.fitnote_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.data.WorkoutProgram
import com.example.fitnote_v2.ui.screens.WorkoutSelector
import com.example.fitnote_v2.ui.theme.Fitnote_v2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fitnote_v2Theme {

                var workouts by remember {
                    mutableStateOf(
                        listOf(
                            WorkoutProgram(
                                name = "Arms & Torso",
                                description = "Complete upper body workout focusing on strength and definition",
                                exercises = emptyList(),
                            ),
                            WorkoutProgram(
                                name = "Legs",
                                description = "Complete Lower body workout focusing on strength and definition",
                                exercises = emptyList(),
                            )
                        )
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val appPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )

                    WorkoutSelector(
                        Modifier.padding(appPadding),
                        workouts,
                        onChooseWorkout = { openWorkoutActivity(it) },
                        onCreateWorkout = { workouts += it },
                        onDeleteWorkout = { workouts -= it }
                    )
                }
            }
        }
    }

    fun openWorkoutActivity (workout: WorkoutProgram) {
        val intent = Intent(this, WorkoutActivity::class.java)
        startActivity(intent)
    }
}