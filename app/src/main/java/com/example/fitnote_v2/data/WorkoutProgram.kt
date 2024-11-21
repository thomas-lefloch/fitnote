package com.example.fitnote_v2.data

data class WorkoutProgram(
    val id: String = "",
    val name: String,
    val description: String,
    val exercises: List<Exercise> = emptyList(),
)

data class Exercise(
    val id: String = "",
    val name: String,
    val description: String,
    val setCount: Int,
    val goal: Goal,
    val sets: List<Set>
)

data class Goal (
    val repCount: Range,
    val weight: Int,
    val rest: Int
)

data class Range (
    val min: Int,
    val max: Int
)


// TODO: implement theses
fun WorkoutProgram.calculateEstimatedDuration(): Int {
    return 30
}

// TODO: rename
fun WorkoutProgram.getDateLastDone(): String {
    return "3 days ago"
}



