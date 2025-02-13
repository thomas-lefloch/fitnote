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

    fun editWorkout(old: WorkoutProgram, new: WorkoutProgram): Boolean {
        // maybe find by id instead of index ? (when sqlite)
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

    fun editSet(exercise: Exercise, oldSetIndex: Int, new: Set): Boolean {
        // maybe find by id instead of index ? (when sqlite)
        exercise.sets[oldSetIndex] = new
        return true
    }
}

private var workoutCollection = mutableListOf(
    WorkoutProgram(
        name = "Séance fullbody pull",
        description = "Entrainement visant tout le corps à travers différents exercices de tirage",
        exercises = mutableListOf(
            Exercise(
                id = "01",
                name = "Tirage vertical",
                note = "Bomber le torse",
                goal = Goal(
                    repMin = 8,
                    repMax = 12,
                    setCount = 3,
                    weight = 45f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 40f, rest = 90),
                    Set(repCount = 12, weight = 40f, rest = 90),
                    Set(repCount = 10, weight = 40f, rest = 90),
                )
            ),
            Exercise(
                id = "02",
                name = "Soulevé de terre - 1 jambe",
                note = "Mouvement du bassin ! rep lente 5-8 sec",
                goal = Goal(
                    repMin = 8,
                    repMax = 10,
                    setCount = 3,
                    weight = 16f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 10, weight = 16f, rest = 90),
                    Set(repCount = 8, weight = 16f, rest = 90),
                    Set(repCount = 6, weight = 16f, rest = 90)
                )
            ),
            Exercise(
                id = "03",
                name = "Tirage horizontal",
                note = "Bomber le torse",
                goal = Goal(
                    repMin = 9,
                    repMax = 12,
                    setCount = 3,
                    weight = 45f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 45f, rest = 90),
                    Set(repCount = 12, weight = 40f, rest = 90),
                    Set(repCount = 9, weight = 40f, rest = 90)
                )
            ),
            Exercise(
                id = "04",
                name = "Soulever de genoux",
                note = "",
                goal = Goal(
                    repMin = 9,
                    repMax = 12,
                    setCount = 3,
                    weight = 0f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 0f, rest = 90),
                    Set(repCount = 12, weight = 0f, rest = 90),
                    Set(repCount = 12, weight = 0f, rest = 90)
                )
            ),
            Exercise(
                id = "05",
                name = "Pall off press",
                note = "30 secondes par côté",
                goal = Goal(
                    repMin = 30,
                    repMax = 30,
                    setCount = 3,
                    weight = 0f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 0f, rest = 60),
                    Set(repCount = 12, weight = 0f, rest = 60),
                    Set(repCount = 12, weight = 0f, rest = 60)
                )
            ),
            Exercise(
                id = "06",
                name = "Adduction",
                note = "Rep lente",
                goal = Goal(
                    repMin = 13,
                    repMax = 15,
                    setCount = 3,
                    weight = 40f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 15, weight = 40f, rest = 90),
                    Set(repCount = 14, weight = 40f, rest = 90),
                    Set(repCount = 11, weight = 40f, rest = 90)
                )
            ),
        )
    ),
    WorkoutProgram(
        name = "Séance fullbody push",
        description = "",
        exercises = mutableListOf(
            Exercise(
                id = "01",
                name = "Push press",
                note = "Explosivité",
                goal = Goal(
                    repMin = 8,
                    repMax = 12,
                    setCount = 3,
                    weight = 25f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 25f, rest = 90),
                    Set(repCount = 12, weight = 25f, rest = 90),
                    Set(repCount = 10, weight = 25f, rest = 90),
                )
            ),
            Exercise(
                id = "02",
                name = "Pompes",
                note = "Sur les poings",
                goal = Goal(
                    repMin = 9,
                    repMax = 12,
                    setCount = 3,
                    weight = 0f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 0f, rest = 90),
                    Set(repCount = 12, weight = 0f, rest = 90),
                    Set(repCount = 6, weight = 0f, rest = 90)
                )
            ),
            Exercise(
                id = "03",
                name = "Planche latéral + abduction",
                note = "30 seconde par coté",
                goal = Goal(
                    repMin = 30,
                    repMax = 30,
                    setCount = 3,
                    weight = 0f,
                    rest = 60
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 0f, rest = 60),
                    Set(repCount = 12, weight = 0f, rest = 60),
                    Set(repCount = 9, weight = 0f, rest = 60)
                )
            ),
            Exercise(
                id = "04",
                name = "Squats bulgares",
                note = "Créer un maximum de flexion de cheville",
                goal = Goal(
                    repMin = 9,
                    repMax = 12,
                    setCount = 3,
                    weight = 16f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 16f, rest = 90),
                    Set(repCount = 12, weight = 16f, rest = 90),
                    Set(repCount = 11, weight = 16f, rest = 90)
                )
            ),
            Exercise(
                id = "05",
                name = "Extensions de mollets",
                note = "Rep lente + immobiliser pendant 4 sec en fin de course",
                goal = Goal(
                    repMin = 30,
                    repMax = 30,
                    setCount = 3,
                    weight = 80f,
                    rest = 90
                ),
                sets = mutableListOf(
                    Set(repCount = 12, weight = 80f, rest = 90),
                    Set(repCount = 12, weight = 80f, rest = 90),
                    Set(repCount = 12, weight = 80f, rest = 90)
                )
            ),
        ),
    ),
    WorkoutProgram(
        name = "Jambes",
        description = "Séance bas du corps compléte optimisé pour l'hypertrophie et la force",
        exercises = mutableListOf(),
    )
)

