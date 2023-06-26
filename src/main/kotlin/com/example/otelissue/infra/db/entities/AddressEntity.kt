package com.example.otelissue.infra.db.entities

import java.util.UUID

data class AddressEntity(
    val id: UUID = UUID.randomUUID(),
    val streetName: String,
    val streetNumber: String,
)
