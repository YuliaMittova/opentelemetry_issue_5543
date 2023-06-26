package com.example.otelissue.propertyListings.presentation.dtos

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.util.UUID

@GraphQLName("StudentInput")
data class StudentRequest(
    val id: UUID,
)
