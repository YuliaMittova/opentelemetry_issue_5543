package com.example.otelissue.infra.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ContactDirective
import com.expediagroup.graphql.server.Schema
import org.springframework.stereotype.Component

@ContactDirective(
    name = "Sample Squad",
    url = "",
    description = "",
)
@GraphQLDescription("Sample Schema")
@Component
class GraphQLSchema : Schema
