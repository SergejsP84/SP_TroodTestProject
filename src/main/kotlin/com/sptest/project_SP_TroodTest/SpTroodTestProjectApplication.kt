package com.sptest.project_SP_TroodTest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@SpringBootApplication
@EnableWebMvc

class SpTroodTestProjectApplication

fun main(args: Array<String>) {

	runApplication<SpTroodTestProjectApplication>(*args)
}
