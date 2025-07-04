package com.example.appointmentbookingapp.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun getRandomColor(): Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}