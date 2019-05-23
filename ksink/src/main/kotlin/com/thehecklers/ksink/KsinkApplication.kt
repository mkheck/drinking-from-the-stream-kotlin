package com.thehecklers.ksink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

@SpringBootApplication
class KsinkApplication

fun main(args: Array<String>) {
	runApplication<KsinkApplication>(*args)
}

@EnableBinding(Sink::class)
class CoffeeDrinker {
	@StreamListener(Sink.INPUT)
	fun drink(coffee: RetailCoffee) = println(coffee)
}

data class RetailCoffee(val id: String, val name: String, val state: State) {
	enum class State {
		WHOLE_BEAN,
		GROUND
	}
}