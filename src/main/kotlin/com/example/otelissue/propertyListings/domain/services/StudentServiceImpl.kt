package com.example.otelissue.propertyListings.domain.services

import com.example.otelissue.propertyListings.domain.interfaces.StudentRepository
import com.example.otelissue.propertyListings.domain.interfaces.StudentService
import com.example.otelissue.propertyListings.presentation.dtos.StudentRequest
import com.example.otelissue.propertyListings.presentation.dtos.StudentResponse
import io.opentelemetry.instrumentation.annotations.WithSpan
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val studentReadRepository: StudentRepository,
) : StudentService {

    @WithSpan
    override suspend fun students(): List<StudentResponse> {
        return studentReadRepository.getAll()
    }

    @WithSpan
    override suspend fun student(input: StudentRequest): StudentResponse {
        return studentReadRepository.getById(input.id)
            ?: throw Exception("There are no students with provided id = ${input.id}")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)
    }
}
