package com.example.reservasalasapp.model

data class Reserva(
    val id: Int? = null,   // ← Opcional, generado automáticamente
    val usuario: String,
    val fecha: String,
    val sala: String,
    val estado: String
)
