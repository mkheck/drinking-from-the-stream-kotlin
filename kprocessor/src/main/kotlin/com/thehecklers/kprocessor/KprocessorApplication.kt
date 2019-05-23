package com.thehecklers.kprocessor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

@SpringBootApplication
class KprocessorApplication

fun main(args: Array<String>) {
	runApplication<KprocessorApplication>(*args)
}

@EnableBinding(Processor::class)
class Transformer {
	private val rnd = Random()

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	fun transform(wCoffee: WholesaleCoffee): RetailCoffee {
		val rCoffee = RetailCoffee(wCoffee.id,
				wCoffee.name,
				if (rnd.nextInt(2) == 0) RetailCoffee.State.WHOLE_BEAN
				else RetailCoffee.State.GROUND)

		println(rCoffee)

		return rCoffee
	}
}

data class RetailCoffee(val id: String, val name: String, val state: State) {
	enum class State {
		WHOLE_BEAN,
		GROUND
	}
}

class WholesaleCoffee(val id: String, val name: String)