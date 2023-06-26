package com.example.otelissue.propertyListings.presentation.graphql

import com.example.otelissue.propertyListings.domain.interfaces.StudentService
import com.example.otelissue.propertyListings.presentation.dtos.StudentRequest
import com.example.otelissue.propertyListings.presentation.dtos.StudentResponse
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import io.opentelemetry.instrumentation.annotations.SpanAttribute
import io.opentelemetry.instrumentation.annotations.WithSpan
import org.springframework.stereotype.Component

@Component
class StudentFetcher(private val studentService: StudentService) : Query {

    @GraphQLDescription("Retrieves information about all students")
    @WithSpan
    suspend fun students(): List<StudentResponse> = studentService.students()

    @GraphQLDescription("Retrieves information about a PropertyListing based on the provided input")
    @WithSpan
    suspend fun studentById(@SpanAttribute input: StudentRequest): StudentResponse =
        studentService.student(input)
}
