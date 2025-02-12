package com.example.fitnote_v2.model

data class WorkoutSession(
    val workoutProgram: String, // uuid
    val setsDone: List<Set> = emptyList()
)
