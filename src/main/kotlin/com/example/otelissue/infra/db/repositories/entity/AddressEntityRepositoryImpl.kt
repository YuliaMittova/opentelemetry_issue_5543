package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.AddressEntity
import io.opentelemetry.instrumentation.annotations.WithSpan
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository

@Repository
class AddressEntityRepositoryImpl : AddressEntityRepository {

    @WithSpan
    override fun findAllById(ids: List<UUID>): Flow<AddressEntity> {
        return flow {
            emit(createTestAddress())
        }
    }

    @WithSpan
    override suspend fun findById(id: UUID): AddressEntity? {
        return createTestAddress()
    }

    private fun createTestAddress() = AddressEntity(
        id = UUID.fromString("078774f7-7f8a-4dd0-91b5-c5eb29b84685"),
        streetName = "Sample street",
        streetNumber = "10a",
    )
}
