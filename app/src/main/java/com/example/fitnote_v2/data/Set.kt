package com.example.fitnote_v2.data

import java.util.Date

data class Set (
    val repCount: Int,
    val weight: Int,
    val rest: Int,
    var completedAt: Date? = null
)
