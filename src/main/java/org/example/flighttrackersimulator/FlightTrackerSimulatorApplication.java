package org.example.flighttrackersimulator;

import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FlightTrackerSimulatorApplication {
	public static void main(String[] args) {
		Simulation.newInstance();
		SpringApplication.run(FlightTrackerSimulatorApplication.class, args);
	}
}
