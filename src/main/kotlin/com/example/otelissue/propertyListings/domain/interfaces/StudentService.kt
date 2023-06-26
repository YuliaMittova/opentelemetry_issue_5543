package com.example.otelissue.propertyListings.domain.interfaces

import com.example.otelissue.propertyListings.presentation.dtos.StudentRequest
import com.example.otelissue.propertyListings.presentation.dtos.StudentResponse

interface StudentService {

    suspend fun students(): List<StudentResponse>

    suspend fun student(input: StudentRequest): StudentResponse
}
