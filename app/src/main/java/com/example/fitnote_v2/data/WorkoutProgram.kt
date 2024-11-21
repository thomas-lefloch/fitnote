package com.example.fitnote_v2.data

data class WorkoutProgram(
    val id: String = "",
    val name: String,
    val description: String,
    var exercises: List<Exercise> = emptyList(),
)

data class Exercise(
    val id: String = "",
    val name: String,
    val note: String,
    val goal: Goal,
    val sets: List<Set> = emptyList()
)

data class Goal (
    val repMin: Int,
    val repMax: Int,
    val setCount: Int,
    val weight: Int,
    val rest: Int
)

// TODO: implement theses
fun WorkoutProgram.calculateEstimatedDuration(): Int {
    return 30
}

// TODO: rename
fun WorkoutProgram.getDateLastDone(): String {
    return "3 days ago"
}



