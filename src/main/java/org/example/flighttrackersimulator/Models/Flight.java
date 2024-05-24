package org.example.flighttrackersimulator.Models;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.flighttrackersimulator.AdditionalSources.FlightSerializer;
import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageProducer;

import java.util.UUID;

@Getter
public class Flight {
    private final UUID id;
    private final Plane plane;
    private final Airport startingAirport;
    private final Airport destinationAirport;

    @Setter
    private Status status;

    public Flight(Plane plane, Airport startingAirport, Airport destinationAirport) {
        this.id = UUID.randomUUID();
        this.plane = plane;
        this.startingAirport = startingAirport;
        this.destinationAirport = destinationAirport;
        this.status = Status.Scheduled;
    }

    public void TakeOff(KafkaProducer<String, String> kafkaProducer) {
        //System.out.println("[" + LocalDateTime.now() + "]: " + "Plane took off!");
        startingAirport.getPlaneList().remove(plane);
        plane.setOnFlight(true);
        status = Status.inFlight;
        KafkaMessageProducer.sendMessage(kafkaProducer, FlightSerializer.serializeFlightToJson(this));
    }

    public void Land(KafkaProducer<String, String> kafkaProducer) {
        //System.out.println("[" + LocalDateTime.now() + "]: " + "Plane landed!");
        destinationAirport.getPlaneList().add(plane);
        plane.setOnFlight(false);
        status = Status.Arrived;
        KafkaMessageProducer.sendMessage(kafkaProducer, FlightSerializer.serializeFlightToJson(this));
    }

    public void next(KafkaProducer<String, String> kafkaProducer) {
        if (plane.hasArrived(destinationAirport.getCoordinates())) {
            Land(kafkaProducer);
        } else {
            plane.move(destinationAirport.getCoordinates());
            //System.out.println("[" + LocalDateTime.now() + "]: " + "Plane moved!");
            KafkaMessageProducer.sendMessage(kafkaProducer, FlightSerializer.serializeFlightToJson(this));
        }
    }

    public enum Status {
        Scheduled, inFlight, Arrived
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", plane=" + plane +
                ", startingAirport=" + startingAirport +
                ", destinationAirport=" + destinationAirport +
                ", status=" + status +
                '}';
    }



}
