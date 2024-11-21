package com.example.fitnote_v2.ui.screens

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Goal
import com.example.fitnote_v2.data.Range
import com.example.fitnote_v2.data.Set
import com.example.fitnote_v2.data.WorkoutProgram

@Composable
fun Workout(workout: WorkoutProgram, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = workout.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
//            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            items(workout.exercises.size) { i ->
                ExerciseCard(exercise = workout.exercises[i])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Composable
fun ExerciseCard(
    exercise: Exercise, modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {

            ExpandableCardHeader(
                exercise.name,
                exercise.description,
                stringResource(
                    R.string.goal,
                    exercise.goal.repCount.min,
                    exercise.goal.repCount.max,
                    exercise.setCount,
                    exercise.goal.rest
                ),
                isExpanded,
                { isExpanded = !isExpanded }
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
                    }, contentDescription = when (expanded) {
                        true -> stringResource(R.string.expanded)
                        false -> stringResource(R.string.collapsed)
                    }
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

@Preview(showBackground = true, widthDp = 540, heightDp = 800)
@Composable
fun WorkoutTrackerPreview() {
    val sampleWorkout = WorkoutProgram(
        id = "1", name = "Arms & Torso", description = "Upper body workout", exercises = listOf(
            Exercise(
                id = "1", name = "Dips", description = "Dips exercise", setCount = 3, goal = Goal(
                    repCount = Range(9, 12), weight = 10, rest = 90
                ), sets = listOf(
                    Set(12, 10, 90), Set(11, 10, 90), Set(9, 10, 90)
                )
            ), Exercise(
                id = "2", name = "Tractions", description = "Pull-ups", setCount = 3, goal = Goal(
                    repCount = Range(3, 12), weight = 10, rest = 90
                ), sets = listOf(
                    Set(12, 10, 90), Set(11, 10, 90), Set(9, 10, 90)
                )
            ), Exercise(
                id = "3",
                name = "Push ups",
                description = "Push ups exercise",
                setCount = 3,
                goal = Goal(
                    repCount = Range(3, 12), weight = 10, rest = 90
                ),
                sets = listOf(
                    Set(12, 10, 90), Set(11, 10, 90), Set(9, 10, 90)
                )
            )
        )
    )

    MaterialTheme {
        Workout(workout = sampleWorkout)
    }
}