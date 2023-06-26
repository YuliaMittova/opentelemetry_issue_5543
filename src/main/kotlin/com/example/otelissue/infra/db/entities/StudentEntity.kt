package com.example.otelissue.infra.db.entities

import java.util.UUID

data class StudentEntity(
    val id: UUID = UUID.randomUUID(),
    var addressId: UUID,
    var vehicleId: UUID,
    val firstName: String,
    val surname: String,
    val age: Int
)
