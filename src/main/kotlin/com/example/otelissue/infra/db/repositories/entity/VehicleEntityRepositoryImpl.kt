package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.VehicleEntity
import io.opentelemetry.instrumentation.annotations.WithSpan
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class VehicleEntityRepositoryImpl : VehicleEntityRepository {

    @WithSpan
    override fun findAllById(ids: List<UUID>): Flow<VehicleEntity> {
        return flow {
            emit(createTestVehicle())
        }
    }

    @WithSpan
    override suspend fun findById(id: UUID): VehicleEntity? {
        return createTestVehicle()
    }

    private fun createTestVehicle() = VehicleEntity(
        id = UUID.fromString("14f541bf-bace-4a50-a82c-8332d695415d"),
        model = "Opel Astra",
        registrationNumber = "111222",
    )

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleEntityRepositoryImpl::class.java)
    }
}
