package org.example.flighttrackersimulator.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
public class Plane {
    private UUID id;
    private int planeCode;
    private String name;
    private boolean isOnFlight;
    private Coordinates currentCoordinates;

    public Plane(int planeCode, String name, boolean isOnFlight, Coordinates currentCoordinates) {
        this.id = UUID.randomUUID();
        this.planeCode = planeCode;
        this.name = name;
        this.isOnFlight = isOnFlight;
        this.currentCoordinates = currentCoordinates;
    }

    public boolean hasArrived(Coordinates destination)
    {
        // Already at the destination
        return (currentCoordinates.getLatDegrees() == (destination.getLatDegrees()))
                && (currentCoordinates.getLongDegrees() == (destination.getLongDegrees()));
    }

    public void move(Coordinates destination) {
        if (hasArrived(destination)) {
            // Already at the destination
            return;
        }

        // Move towards the destination
        double latDifference = destination.getLatDegrees() - currentCoordinates.getLatDegrees();
        double longDifference = destination.getLongDegrees() - currentCoordinates.getLongDegrees();

        double latMovement = Math.signum(latDifference);
        double longMovement = Math.signum(longDifference);

        // Update latitude
        if (Math.abs(latDifference) >= 0.1) {
            currentCoordinates.setLatDegrees(currentCoordinates.getLatDegrees() + latMovement * 0.1);
        }

        // Update longitude
        if (Math.abs(longDifference) >= 0.1) {
            currentCoordinates.setLongDegrees(currentCoordinates.getLongDegrees() + longMovement * 0.1);
        }

        if (Math.abs(latDifference) < 0.1 && Math.abs(longDifference) < 0.1) {
            currentCoordinates.setLatDegrees(destination.getLatDegrees());
            currentCoordinates.setLongDegrees(destination.getLongDegrees());
        }
    }
}
