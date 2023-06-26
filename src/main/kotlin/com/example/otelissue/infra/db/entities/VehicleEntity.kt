package com.example.otelissue.infra.db.entities

import java.util.UUID

data class VehicleEntity(
    val id: UUID = UUID.randomUUID(),
    val model: String,
    val registrationNumber: String,
)
