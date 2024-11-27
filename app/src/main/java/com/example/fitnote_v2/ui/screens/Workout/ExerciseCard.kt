package com.example.fitnote_v2.ui.screens.Workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnote_v2.R
import com.example.fitnote_v2.data.Exercise
import com.example.fitnote_v2.data.Set
import java.util.Date


private val setCompletedColor = Color(0xFFA0B397)

@Composable
fun ExerciseCard(
    exercise: Exercise, modifier: Modifier = Modifier
) {
    // FIXME: isExpanded's value is reset when is goes out of screen
    var isExpanded by remember { mutableStateOf(true) }
    // FIXME: data is reset when the component goes out of screen,  user data will be correclty manage when I start implementing the database
    var sets by remember { mutableStateOf(exercise.sets) }

    val horizontalCardPadding = 16.dp

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp,horizontal = horizontalCardPadding)) {

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
            )

            Spacer(modifier = Modifier.height(6.dp))

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

            sets.forEachIndexed { i, set ->
                TableRow(set, horizontalCardPadding, onSetCompletion = {
                    sets = sets.toMutableList().apply {
                        this[i] = set.copy(completedAt = Date(System.currentTimeMillis()))
                    }
                })
            }

            OutlinedButton(
                onClick = { sets += exercise.sets[exercise.sets.size - 1].copy(completedAt = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(stringResource(R.string.button_add_set))
            }
        }
    }
}

@Composable
private fun ExpandableCardHeader(
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
private fun TableTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TableRow(set: Set, horizontalCardPadding: Dp, onSetCompletion: (Set) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + horizontalCardPadding.roundToPx() * 2
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
            .background(if (set.completedAt == null) Color.Transparent else setCompletedColor)
            .padding(horizontal = horizontalCardPadding),
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
            onClick = { onSetCompletion(set) },
            modifier = Modifier.width(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.done),
            )
        }
    }
}
