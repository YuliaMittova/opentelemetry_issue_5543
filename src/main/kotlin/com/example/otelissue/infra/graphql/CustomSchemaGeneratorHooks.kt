package com.example.otelissue.infra.graphql

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {

    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        UUID::class -> ExtendedScalars.UUID
        else -> null
    }

    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.first().type ?: type
        Set::class -> List::class.createType(type.arguments)
        else -> type
    }

    override fun isValidSuperclass(kClass: KClass<*>): Boolean {
        return when {
            kClass.isSubclassOf(BeanFactoryAware::class) -> false
            else -> super.isValidSuperclass(kClass)
        }
    }
}
