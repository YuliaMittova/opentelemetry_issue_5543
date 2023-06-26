package com.example.otelissue.infra.db.repositories.read

import com.example.otelissue.infra.db.entities.AddressEntity
import com.example.otelissue.infra.db.entities.StudentEntity
import com.example.otelissue.infra.db.entities.VehicleEntity
import com.example.otelissue.infra.db.repositories.entity.AddressEntityRepository
import com.example.otelissue.infra.db.repositories.entity.StudentEntityRepository
import com.example.otelissue.infra.db.repositories.entity.VehicleEntityRepository
import com.example.otelissue.propertyListings.domain.interfaces.StudentRepository
import com.example.otelissue.propertyListings.presentation.dtos.StudentResponse
import io.opentelemetry.instrumentation.annotations.WithSpan
import java.util.UUID
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class StudentRepositoryImpl(
    private val studentEntityRepository: StudentEntityRepository,
    private val addressEntityRepository: AddressEntityRepository,
    private val vehicleEntityRepository: VehicleEntityRepository,
) : StudentRepository {

    @WithSpan
    override suspend fun getAll(): List<StudentResponse> {
        return studentEntityRepository.findAll().toList().withRelations()
    }

    @WithSpan
    override suspend fun getById(id: UUID): StudentResponse? {
        return studentEntityRepository.findById(id)?.withRelations()
    }

    private suspend fun StudentEntity.withRelations(): StudentResponse {
        val addressEntity = addressEntityRepository.findById(this.addressId)
        val vehicleEntity = vehicleEntityRepository.findById(this.vehicleId)
        return StudentResponse(
            id = this.id,
            addressId = this.addressId,
            vehicleId = this.vehicleId,
            address = addressEntity,
            vehicle = vehicleEntity,
            firstName = this.firstName,
            surname = this.surname,
            age = this.age,
        )
    }

    private suspend fun List<StudentEntity>.withRelations(): List<StudentResponse> {
        return chunked(100).map { studentBatch ->
            val addresses = addressesByStudentId(studentBatch)
            val vehicles = vehiclesByStudentId(studentBatch)

            studentBatch.map { student ->
                val address = addresses[student.addressId]
                val vehicle = vehicles[student.vehicleId]

                StudentResponse(
                    id = student.id,
                    addressId = student.addressId,
                    vehicleId = student.vehicleId,
                    address = address,
                    vehicle = vehicle,
                    firstName = student.firstName,
                    surname = student.surname,
                    age = student.age,
                )
            }
        }.flatten()
    }

    @WithSpan
    private suspend fun addressesByStudentId(
        studentBatch: List<StudentEntity>,
    ): Map<UUID, AddressEntity> {
        val addressIds = studentBatch.map { it.addressId }.distinct()
        return addressEntityRepository.findAllById(addressIds).toList()
            .associateBy { it.id }
    }

    @WithSpan
    private suspend fun vehiclesByStudentId(
        studentBatch: List<StudentEntity>,
    ): Map<UUID, VehicleEntity> {
        val vehicleIds = studentBatch.map { it.vehicleId }.distinct()
        return vehicleEntityRepository.findAllById(vehicleIds).toList()
            .associateBy { it.id }
    }
}
