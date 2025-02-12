package com.example.fitnote_v2.repository

import com.example.fitnote_v2.model.Exercise
import com.example.fitnote_v2.model.Goal
import com.example.fitnote_v2.model.Set
import com.example.fitnote_v2.model.WorkoutProgram

object WorkoutRepository {
    fun getWorkouts(): List<WorkoutProgram> = workoutCollection
    fun getWorkoutByIndex(index: Int): WorkoutProgram {
        return workoutCollection[index]
    }
    fun createWorkout(new: WorkoutProgram): Boolean {
        return workoutCollection.add(new)
    }
    fun deleteWorkout(toDelete: WorkoutProgram): Boolean {
        return workoutCollection.remove(toDelete)
    }
    fun editWorkout(old :WorkoutProgram, new: WorkoutProgram): Boolean {
        // maybe find by id  ? (when sqlite)
        val i = workoutCollection.indexOf(old)
        if (i == -1) return false
        workoutCollection[i] = new
        return true
    }
    fun addExerciseToWorkout(workout: WorkoutProgram, exercise: Exercise): Boolean {
        return workout.exercises.add(exercise)
    }
    fun addSetToExercise(exercise: Exercise, set: Set): Boolean {
        return exercise.sets.add(set)
    }
    fun editSet(exercise: Exercise, old: Set, new: Set): Boolean {
        // maybe find by id  ? (when sqlite)
        val i = exercise.sets.indexOf(old)
        if (i == -1) return false
        exercise.sets[i] = new
        return true
    }
}

private var workoutCollection = mutableListOf(
    WorkoutProgram(
        name = "Arms & Torso",
        description = "Complete upper body workout focusing on strength and definition",
        exercises = mutableListOf(
            Exercise(
                id = "EX001",
                name = "Barbell Squats",
                note = "Focus on proper form and depth",
                goal = Goal(
                    repMin = 8,
                    repMax = 12,
                    setCount = 4,
                    weight = 135,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 10, weight = 95f, rest = 90),
                    Set(repCount = 10, weight = 115f, rest = 90),
                    Set(repCount = 8, weight = 135f, rest = 120),
                )
            ),
            Exercise(
                id = "EX002",
                name = "Bench Press",
                note = "Maintain steady tempo and full range of motion",
                goal = Goal(
                    repMin = 6,
                    repMax = 10,
                    setCount = 4,
                    weight = 185,
                    rest = 120
                ),
                sets = mutableListOf(
                    Set(repCount = 10, weight = 135f, rest = 90),
                    Set(repCount = 8, weight = 155f, rest = 120),
                    Set(repCount = 6, weight = 185f, rest = 150)
                )
            ),
            Exercise(
                id = "EX003",
                name = "Deadlifts",
                note = "Engage core and maintain neutral spine",
                goal = Goal(
                    repMin = 5,
                    repMax = 8,
                    setCount = 3,
                    weight = 225,
                    rest = 180
                ),
                sets = mutableListOf(
                    Set(repCount = 8, weight = 185f, rest = 120),
                    Set(repCount = 6, weight = 225f, rest = 180),
                    Set(repCount = 5, weight = 225f, rest = 180)
                )
            ),
            Exercise(
                id = "EX004",
                name = "Pull-Ups",
                note = "Full extension at bottom, chin over bar at top",
                goal = Goal(
                    repMin = 6,
                    repMax = 10,
                    setCount = 3,
                    weight = 0,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 8, weight = 0f, rest = 90),
                    Set(repCount = 7, weight = 0f, rest = 90),
                    Set(repCount = 6, weight = 0f, rest = 90)
                )
            ),
        )
    ),
    WorkoutProgram(
        name = "Legs",
        description = "Complete Lower body workout focusing on strength and definition",
        exercises = mutableListOf(),
    )
)

