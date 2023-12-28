package com.example.nedemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NedemoApplication

fun main(args: Array<String>) {
    runApplication<NedemoApplication>(*args)
}
