package com.example.otelissue.propertyListings.domain.interfaces

import com.example.otelissue.propertyListings.presentation.dtos.StudentResponse
import java.util.UUID

interface StudentRepository {
    suspend fun getAll(): List<StudentResponse>

    suspend fun getById(id: UUID): StudentResponse?
}
