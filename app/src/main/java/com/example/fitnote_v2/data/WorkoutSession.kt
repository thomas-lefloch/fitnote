package com.example.fitnote_v2.data

data class WorkoutSession(
    val workoutProgram: String, // uuid
    val setsDone: List<Set> = emptyList()
)
