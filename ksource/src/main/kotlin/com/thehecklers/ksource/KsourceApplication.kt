package com.thehecklers.ksource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@SpringBootApplication
class KsourceApplication

fun main(args: Array<String>) {
	runApplication<KsourceApplication>(*args)
}

@EnableBinding(Source::class)
@EnableScheduling
class CoffeeShipper(private val source: Source, private val generator: CoffeeGenerator) {
	@Scheduled(fixedRate = 1000)
	fun send() = source.output()
			.send(MessageBuilder.withPayload(generator.generate()).build())
}

@Component
class CoffeeGenerator {
	private val names = "Kaldi's Coffee, Pike Place, Espresso Roast".split(", ")
	private val rnd: Random = Random()

	fun generate() = WholesaleCoffee(UUID.randomUUID().toString(),
			names.get(rnd.nextInt(names.size)))
}

class WholesaleCoffee(val id: String, val name: String)