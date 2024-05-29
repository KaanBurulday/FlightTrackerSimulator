package org.example.flighttrackersimulator;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.flighttrackersimulator.AdditionalSources.DistanceCalculator;
import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageConsumer;
import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageProducer;
import org.example.flighttrackersimulator.AdditionalSources.RandomAndProbabilitySource;
import org.example.flighttrackersimulator.Models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {
    @Getter
    @Setter
    private static List<Airport> airportList;
    @Getter
    @Setter
    private static List<Plane> planeList;
    @Getter
    @Setter
    private static List<Flight> flights;
    @Getter
    @Setter
    private static volatile boolean stopApp;
    @Getter
    @Setter
    private static double airportAccessDistance;
    private static ScheduledExecutorService executorService;

    @Getter
    private static KafkaProducer<String, String> kafkaProducer = KafkaMessageProducer.createKafkaProducer();
    @Getter
    private static KafkaConsumer<String, String> kafkaConsumer = KafkaMessageConsumer.createKafkaConsumer();

    public static void newInstance() {
        airportList = new ArrayList<>();
        planeList = new ArrayList<>();
        flights = new ArrayList<>();
        stopApp = false;
        airportAccessDistance = 150;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static List<Airport> generate(int iteration) {
        newInstance();
        // Generation of airports
        List<Airport> airports = new ArrayList<>();
        for (int i = 0; i < iteration; i++) {
            airports.add(new Airport(
                    i,
                    "Airport-" + i,
                    Coordinates.getRandomBasicCoordinates(),
                    RandomAndProbabilitySource.randInt(5, 20)
            ));
        }
        // Generation of planes and placing them into the airports
        int count = 0;
        for (Airport airport : airports) {
            for (int i = 0; i < airport.getCapacity(); i++) {
                count++;
                Coordinates planeCoordinates = new Coordinates(airport.getCoordinates().getLatDegrees(),
                        airport.getCoordinates().getLongDegrees());
                Plane plane = new Plane(
                        count,
                        "Plane-" + count,
                        false,
                        planeCoordinates);
                airport.addPlane(plane);
                planeList.add(plane);
            }
        }
        // Filling the accessible airports list of each airport
        double distance = 0;
        for (Airport airport : airports) {
            for (Airport airport1 : airports) {
                if (airport != airport1) {
                    distance = DistanceCalculator.calculate(
                            airport.getCoordinates().getLatDegrees(),
                            airport.getCoordinates().getLongDegrees(),
                            airport1.getCoordinates().getLatDegrees(),
                            airport1.getCoordinates().getLongDegrees()
                    );
                    if (distance <= airportAccessDistance) {
                        airport.getAccessibleAirportCodes().add(airport1.getCode());
                    }
                }
            }
        }
        return airports;
    }

    public static void start() {
        executorService.scheduleAtFixedRate(() -> {
            try
            {
                for (Flight flight : getFlights()) {
                    flight.next(kafkaProducer);
                }
                for (Airport airport : getAirportList()) {
                    if (!airport.getPlaneList().isEmpty()) {
                        List<Plane> planeListCopy = new ArrayList<>(airport.getPlaneList());
                        for (Plane plane : planeListCopy) {
                            Airport randomAccessibleAirport = getAirportList().get(airport.getAccessibleAirportCodes().get(RandomAndProbabilitySource.randInt(0, airport.getAccessibleAirportCodes().size() - 1)));
                            Flight flight = new Flight(plane, airport, randomAccessibleAirport);
                            flights.add(flight);
                            flight.TakeOff(kafkaProducer);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
        System.err.println("Executor ended");
    }


    public static void stop() {
        stopApp = true;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }


    public static void consumeMessages() {
        try
        {
            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                executor.submit(() -> {
                    KafkaMessageConsumer.consumeMessages(kafkaConsumer);
                });
            }
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void consumeMessage() {
        try
        {
            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                executor.submit(() -> {
                    KafkaMessageConsumer.consumeMessage(kafkaConsumer);
                });
            }
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
