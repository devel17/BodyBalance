package com.dev.bb

import com.dev.bb.api.configuration.OpenApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
//@Import(OpenApi::class)
open class BbApplication

fun main(args: Array<String>) {
	runApplication<BbApplication>(*args)
}
