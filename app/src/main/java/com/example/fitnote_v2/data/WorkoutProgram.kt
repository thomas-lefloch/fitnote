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
    val defaultSet: Set
)


// TODO: implement theses
fun WorkoutProgram.calculateEstimatedDuration(): Int {
    return 30
}

// TODO: rename
fun WorkoutProgram.getDateLastDone(): String {
    return "3 days ago"
}



