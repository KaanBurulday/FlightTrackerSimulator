package org.example.flighttrackersimulator.AdditionalSources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flighttrackersimulator.Models.Flight;

public class FlightSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serializeFlightToJson(Flight flight) {
        try {
            return objectMapper.writeValueAsString(flight);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
