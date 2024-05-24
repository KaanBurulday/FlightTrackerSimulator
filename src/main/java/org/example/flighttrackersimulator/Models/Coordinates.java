package org.example.flighttrackersimulator.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.flighttrackersimulator.AdditionalSources.DistanceCalculator;
import org.example.flighttrackersimulator.AdditionalSources.Pair;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class Coordinates {
    private double latDegrees;
    private String latitude;
    private double longDegrees;
    private String longitude;

    public Coordinates(double latDegrees, double longDegrees) {
        this.latDegrees = latDegrees;
        this.longDegrees = longDegrees;
    }

    @Override
    public String toString() {
        return String.format("(%f\u00B0 %s, %f\u00B0 %s)", latDegrees, latitude,
                longDegrees, longitude);
    }

    public Pair<Integer, Integer> getPixelCoordinates(int mapWidth, int mapHeight)
    {
        double mapWidthDegrees = 100.0;
        double mapHeightDegrees = 100.0;

        int pixelX = (int) ((longDegrees / mapWidthDegrees) * mapWidth);
        int pixelY = (int) ((latDegrees / mapHeightDegrees) * mapHeight);

        return new Pair<>(pixelX, pixelY);
    }

    public static Coordinates getRandomBasicCoordinates()
    {
        double latMax = 90.0;
        double latMin = -90.0;
        double longMax = 180.0;
        double longMin = -180.0;

        Random random = new Random();
        double latRand = latMin + (latMax - latMin) * random.nextDouble();
        double longRand = longMin + (longMax - longMin) * random.nextDouble();
        return new Coordinates(latRand, longRand);
    }

    public double distanceTo(Coordinates coordinates) {
        return DistanceCalculator.calculate(latDegrees, longDegrees, coordinates.getLatDegrees(), coordinates.getLongDegrees());
    }
}
