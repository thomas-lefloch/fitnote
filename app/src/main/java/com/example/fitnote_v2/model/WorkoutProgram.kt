package com.example.fitnote_v2.model

data class WorkoutProgram(
    val id: String = "",
    val name: String,
    val description: String,
    var exercises: MutableList<Exercise> = mutableListOf(),
)

data class Exercise(
    val id: String = "",
    val name: String,
    val note: String,
    val goal: Goal,
    var sets: MutableList<Set> = mutableListOf()
)

data class Goal (
    val repMin: Int,
    val repMax: Int,
    val setCount: Int,
    val weight: Int,
    val rest: Int
)

fun WorkoutProgram.calculateEstimatedDuration(): Int {
    return exercises.size * 6 // TODO: proper implementation
}

// TODO: rename & implement
fun WorkoutProgram.getDateLastDone(): String {
    return "3 days ago"
}



