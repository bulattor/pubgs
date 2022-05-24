package ru.tinkoff.fintech.pubgs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PubgsApplication

fun main(args: Array<String>) {
	runApplication<PubgsApplication>(*args)
}
