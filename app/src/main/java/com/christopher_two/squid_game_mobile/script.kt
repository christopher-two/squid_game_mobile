package com.christopher_two.squid_game_mobile

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

// Data class con todos los campos como String
data class Player(
    val sex: String,
    val fullName: String,
    val height: String,
    val weight: String,
    val city: String,
    val country: String,
    val financialHistory: String,
    val age: String,
    val civilStatus: String,
    val numPlayer: String,
    val gender: String
)

// Lista completa de jugadores con los datos exactos del txt
val playersList = listOf(
    Player(
        sex = "Masculino",
        fullName = "Christopher",
        height = "180",
        weight = "100",
        city = "Uruapan York",
        country = "Polo Norte",
        financialHistory = "-174",
        age = "20",
        civilStatus = "Casado",
        numPlayer = "13",
        gender = "No binario"
    ),
    Player(
        sex = "Macho",
        fullName = "Juan",
        height = "185",
        weight = "102",
        city = "Uruapan",
        country = "México",
        financialHistory = "-9289098120",
        age = "20",
        civilStatus = "viudo",
        numPlayer = "10",
        gender = "normal"
    ),
    Player(
        sex = "Masculino",
        fullName = "Oswaldo",
        height = "170",
        weight = "63",
        city = "México",
        country = "Uruapan",
        financialHistory = "-2123213",
        age = "20",
        civilStatus = "Soltero",
        numPlayer = "#27",
        gender = "normal"
    ),
    Player(
        sex = "Prefiero no decirlo",
        fullName = "Zabdiel",
        height = "172",
        weight = "84",
        city = "Uruapan",
        country = "México",
        financialHistory = "-0",
        age = "21",
        civilStatus = "es complicado",
        numPlayer = "89",
        gender = "Non-binary"
    ),
    Player(
        sex = "Masculino",
        fullName = "Pulido",
        height = "170",
        weight = "70",
        city = "México",
        country = "Uruapan",
        financialHistory = "0",
        age = "22",
        civilStatus = "Soltero",
        numPlayer = "66",
        gender = "normal"
    ),
    Player(
        sex = "Masculino",
        fullName = "Brandon",
        height = "185",
        weight = "89",
        city = "Uruapan",
        country = "México",
        financialHistory = "0",
        age = "21",
        civilStatus = "Soltero",
        numPlayer = "6",
        gender = "Normal"
    ),
    Player(
        sex = "Masculino",
        fullName = "Gamboa",
        height = "165",
        weight = "66",
        city = "México",
        country = "San Anyork",
        financialHistory = "1400",
        age = "19",
        civilStatus = "Soltero",
        numPlayer = "12",
        gender = "El genero es para la musica"
    ),
    Player(
        sex = "Masculino",
        fullName = "José Luis Ruiz Rincón",
        height = "186",
        weight = "98",
        city = "San John",
        country = "Perú",
        financialHistory = "-88,000",
        age = "20",
        civilStatus = "Es complicado",
        numPlayer = "444",
        gender = "Audi R8"
    ),
    Player(
        sex = "Hombre",
        fullName = "Drinity",
        height = "170",
        weight = "61",
        city = "Tokyo-3",
        country = "Japón",
        financialHistory = "-200,000",
        age = "20",
        civilStatus = "Soltero",
        numPlayer = "137",
        gender = "Normal"
    )
)

// Función para subir todos los jugadores
suspend fun uploadAllPlayers() {
    val db = Firebase.firestore

    playersList.forEachIndexed { index, player ->
        try {
            db.collection("players")
                .document("$index")  // ID personalizado
                .set(player)
                .await()
            println("✅ Subido: ${player.fullName}")
        } catch (e: Exception) {
            println("❌ Error subiendo ${player.fullName}: ${e.message}")
        }
    }
}