package org.example.flighttrackersimulator;

import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageProducer;
import org.example.flighttrackersimulator.Models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SimulationRestController {
    @GetMapping("/Coordinates")
    public List<Coordinates> ShowCoordinates(@RequestParam("iteration") int iteration) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < iteration; i++) {
            coordinates.add(Coordinates.getRandomBasicCoordinates());
        }
        return coordinates;
    }

    @GetMapping("/GenerateEnv")
    public String GenerateEnv(@RequestParam("iteration") int iteration) {
        System.out.println("Environment generated!");
        Simulation.setAirportList(Simulation.generate(iteration));
        return "Environment Generated!";
    }

    @GetMapping("/GetEnv")
    @ResponseBody
    public List<Airport> GetEnv() {
        return Simulation.getAirportList();
    }

    @GetMapping("/Planes")
    public List<Plane> GetAirplanes() {
        return Simulation.getPlaneList();
    }

    @GetMapping("/EnvStart")
    public String EnvStart() {
        System.out.println("Environment started!");
        Simulation.start();
        return "Environment Started!";
    }

    @GetMapping("/EnvStop")
    public String EnvStop() {
        System.err.println("Environment stopped!");
        Simulation.stop();
        return "Environment Stopped!";
    }

    @GetMapping("/Flights")
    public List<Flight> Flights() {
        return Simulation.getFlights();
    }

    @GetMapping("/Consumer/Messages")
    public void ConsumeMessage()
    {
        Simulation.consumeMessages();
    }

    @GetMapping("/Consumer/Message")
    public void ConsumeMessages()
    {
        Simulation.consumeMessage();
    }
}
