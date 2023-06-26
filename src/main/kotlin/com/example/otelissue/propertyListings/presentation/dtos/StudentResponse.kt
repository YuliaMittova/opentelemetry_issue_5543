package com.example.otelissue.propertyListings.presentation.dtos

import com.example.otelissue.infra.db.entities.AddressEntity
import com.example.otelissue.infra.db.entities.VehicleEntity
import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.util.UUID

@GraphQLName("Student")
data class StudentResponse(
    val id: UUID,
    val addressId: UUID,
    val vehicleId: UUID,
    val address: AddressEntity? = null,
    val vehicle: VehicleEntity? = null,
    val firstName: String,
    val surname: String,
    val age: Int,
)
