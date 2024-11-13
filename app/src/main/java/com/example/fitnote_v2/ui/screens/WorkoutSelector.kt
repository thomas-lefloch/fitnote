package com.example.fitnote_v2.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.WorkoutProgram
import com.example.fitnote_v2.data.calculateEstimatedDuration
import com.example.fitnote_v2.data.getDateLastDone
import com.example.fitnote_v2.ui.components.ConfirmDialog
import com.example.fitnote_v2.ui.components.DefaultDialog

@Composable
fun WorkoutSelector(
    modifier: Modifier = Modifier,
    workouts: List<WorkoutProgram>,
    onCreateWorkout: (WorkoutProgram) -> Unit,
    onDeleteWorkout: (WorkoutProgram) -> Unit,
    onChooseWorkout: (WorkoutProgram) -> Unit,
) {
    var addingWorkout by remember { mutableStateOf(false) }
    var workoutToDelete by remember { mutableStateOf<WorkoutProgram?>(null) }
    var workoutToEdit by remember { mutableStateOf<WorkoutProgram?>(null) }

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.workout_selector_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(Modifier.weight(1f)) {
            if (workouts.isEmpty()) {
                item {
                    Column(
                        Modifier.fillParentMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.no_workouts), textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(workouts.size) { i ->
                    WorkoutCard(workouts[i], onChooseWorkout = onChooseWorkout, onEditWorkout = {
                        workoutToEdit = workouts[i]
                    }, onDeleteWorkout = {
                        workoutToDelete = workouts[i]
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Button(
            onClick = { addingWorkout = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(R.string.button_add_workout),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    if (addingWorkout) {
        WorkoutDialog(onDismiss = { addingWorkout = false },
            onConfirmWorkout = { onCreateWorkout(it); addingWorkout = false })
    } else if (workoutToDelete != null) {
        ConfirmDialog(
            onDismissRequest = { workoutToDelete = null },
            onConfirmRequest = { onDeleteWorkout(workoutToDelete!!); workoutToDelete = null },
            title = stringResource(R.string.delete_workout_confirmation_title),
            content = stringResource(
                R.string.delete_workout_confirmation_message, workoutToDelete!!.name
            ),
            confirm = stringResource(R.string.delete),
        )
    } else if (workoutToEdit != null) {
        WorkoutDialog(
            onDismiss = { workoutToEdit = null },
            onConfirmWorkout = {/*TODO: update workout waiting for database impl*/; workoutToEdit =
                null
            },
            workout = workoutToEdit
        )
    }

}

@Composable
fun WorkoutCard(
    workout: WorkoutProgram,
    modifier: Modifier = Modifier,
    onEditWorkout: (WorkoutProgram) -> Unit,
    onDeleteWorkout: (WorkoutProgram) -> Unit,
    onChooseWorkout: (WorkoutProgram) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp, 12.dp)) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(text = { Text(stringResource(R.string.edit_workout)) },
                            leadingIcon = {
                                Icon(Icons.Default.Edit, null)
                            },
                            onClick = {
                                onEditWorkout(workout)
                                showMenu = false
                            })
                        DropdownMenuItem(text = { Text(stringResource(R.string.delete_workout)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Delete,
                                    null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = {
                                onDeleteWorkout(workout)
                                showMenu = false
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = workout.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    Text(
                        text = stringResource(R.string.exercises_count, workout.exercises.size),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(8.dp, 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(shape = MaterialTheme.shapes.small) {
                    Text(
                        text = stringResource(
                            R.string.duration_estimate_minutes, workout.calculateEstimatedDuration()
                        ),
                        modifier = Modifier.padding(8.dp, 4.dp),
                        style = MaterialTheme.typography.labelLarge,
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.last_done, workout.getDateLastDone()),
                    style = MaterialTheme.typography.labelSmall,
                )
                Button(
                    onClick = { onChooseWorkout(workout) },
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(
                        text = stringResource(R.string.button_start_workout),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

            }
        }
    }
}

@Composable
fun WorkoutDialog(
    onDismiss: () -> Unit,
    onConfirmWorkout: (WorkoutProgram) -> Unit,
    workout: WorkoutProgram? = null
) {
    var name by remember { mutableStateOf(workout?.name ?: "") }
    var description by remember { mutableStateOf(workout?.description ?: "") }

    DefaultDialog(onDismissRequest = onDismiss,
        title = stringResource(R.string.dialog_title_new_workout),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.label_workout_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.label_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        },
        confirm = stringResource(R.string.confirm),
        isConfirmButtonEnabled = name.isNotBlank(),
        onConfirmRequest = {
            val newWorkout =
                workout?.copy(name = name, description = description) ?: WorkoutProgram(
                    name = name,
                    description = description
                )

            onConfirmWorkout(newWorkout)
        })
}
