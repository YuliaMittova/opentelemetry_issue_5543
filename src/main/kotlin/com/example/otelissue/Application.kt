package com.example.otelissue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.otelissue"])
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}
