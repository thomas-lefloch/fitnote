package com.example.fitnote_v2.model

import java.util.Date

data class Set (
    val repCount: Int,
    val weight: Float,
    val rest: Int,
    var completedAt: Date? = null
)
