package com.example.fichadesuperheroi

data class Heroi(
    val codinome: String,
    val alinhamento: String,
    val poderes: List<String>,
    val avatarResId: Int
)