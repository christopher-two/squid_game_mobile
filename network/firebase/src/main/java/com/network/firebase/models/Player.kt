package com.network.firebase.models

data class Player(
    val id: String = "",
    val sex: String = "Male",
    val gender: String = "Normal",
    val fullName: String = "Christopher Alejandro Maldonado Chavez",
    val height: Int = 180,
    val weight: Int = 100,
    val city: String = "uruapan york",
    val country: String = "Michoacan",
    val financialHistory: Int = 900000,
    val age: Int = 20,
    val civilStatus: String = "Single",
    val numPlayer: Int = 13,
    val image: String? = null,
)