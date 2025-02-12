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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.model.WorkoutProgram
import com.example.fitnote_v2.repository.WorkoutRepository
import com.example.fitnote_v2.ui.screens.WorkoutSelector
import com.example.fitnote_v2.ui.theme.Fitnote_v2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fitnote_v2Theme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val appPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )

                    WorkoutSelector(
                        Modifier.padding(appPadding),
                        WorkoutRepository.getWorkouts(),
                        onChooseWorkout = { openWorkoutActivity(it) },
                        onCreateWorkout = WorkoutRepository::createWorkout,
                        onDeleteWorkout = WorkoutRepository::deleteWorkout,
                        onEditWorkout = WorkoutRepository::editWorkout
                    )
                }
            }
        }
    }

    // maybe use routing ?
    private fun openWorkoutActivity(workoutIndex: Int) {
        val intent = Intent(this, WorkoutActivity::class.java)
        intent.putExtra("workoutIndex", workoutIndex)
        startActivity(intent)
    }
}