package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.StudentEntity
import io.opentelemetry.instrumentation.annotations.WithSpan
import java.util.UUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class StudentEntityRepositoryImpl : StudentEntityRepository {

    @WithSpan
    override fun findAll(): Flow<StudentEntity> {
        return flow {
//            emit(createTestStudent())
            var i = 0
            while (i < 10) {
                logger.error("${System.currentTimeMillis()} emitting $i")
                emit(createTestStudent().copy(firstName = "name + $i"))
                delay(500L)
                i++
            }
        }
    }

    @WithSpan
    override suspend fun findById(id: UUID): StudentEntity? {
        return createTestStudent()
    }

    private fun createTestStudent() = StudentEntity(
        id = UUID.randomUUID(),
        addressId = UUID.randomUUID(),
        vehicleId = UUID.randomUUID(),
        firstName = "Adam",
        surname = "Smith",
        age = 30,
    )

    companion object {
        private val logger = LoggerFactory.getLogger(StudentEntityRepositoryImpl::class.java)
    }
}
