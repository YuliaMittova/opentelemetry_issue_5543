plugins {
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("com.google.cloud.tools.jib") version "3.3.1"
    id("com.apollographql.apollo3") version "3.8.0"
}

version = "0.1"
group = "com.example.otelissue"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val kotlinVersion = project.properties["kotlinVersion"]
val coroutinesVersion = project.properties["coroutinesVersion"]
val testcontainersVersion = project.properties["testcontainersVersion"]

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc") // Needed for Flyway
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.15")

    implementation("io.netty:netty-all")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.72.Final:osx-aarch_64")

    implementation("com.expediagroup:graphql-kotlin-spring-server:6.4.0")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:6.4.0")
    implementation("com.graphql-java:graphql-java-extended-scalars:20.0")

    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.24.0")
    implementation("org.json:json:20230227")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.googlecode.libphonenumber:libphonenumber:8.12.28")
    // need to add this for AWS to operate because apollo has okhttp with older version
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    implementation("net.datafaker:datafaker:1.9.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.3")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")

    implementation("com.apollographql.apollo3:apollo-runtime:3.8.0") // Only needed in tests (and for the build step)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = maxOf(Runtime.getRuntime().availableProcessors() / 2, 1)
}

tasks {
    jib {
        to {
            image = "gcr.io/myapp/jib-image"
        }
    }
}

gitProperties {
    keys = listOf(
        "git.branch",
        "git.build.version",
        "git.commit.id",
        "git.commit.id.abbrev",
        "git.commit.time",
        "git.tags",
    )
}

apollo {
    service("service") {
        packageName.set("com.generated.test.graphql")
        schemaFile.set(
            file(
                "${project.projectDir}/src/main/kotlin/com/example/otelissue/infra/graphql/schema.graphqls",
            ),
        )
        srcDir("${project.projectDir}/src/test/resources/gql-requests")
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    filter {
        exclude("**/build/generated/source/apollo/**")
        exclude { entry -> entry.file.toString().contains("generated") }
        exclude("**/generated/source/**")
    }
}
