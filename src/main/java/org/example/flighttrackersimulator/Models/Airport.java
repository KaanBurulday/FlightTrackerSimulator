package org.example.flighttrackersimulator.Models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Airport {
    private final UUID id;
    private final int code;
    private final String name;
    private final Coordinates coordinates;
    private final int capacity;
    private final List<Integer> accessibleAirportCodes;

    private ArrayList<Plane> planeList;

    public Airport(int code, String name, Coordinates coordinates, int capacity) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.coordinates = coordinates;
        this.capacity = capacity;
        this.accessibleAirportCodes = new ArrayList<>();
        this.planeList = new ArrayList<>();
    }

    public void addPlane(Plane plane)
    {
        this.planeList.add(plane);
    }

    public void addAccessibleAirport(Airport airport)
    {
        this.accessibleAirportCodes.add(airport.getCode());
    }

    public void planeTakeOff(Plane plane)
    {
        planeList.remove(plane);
    }

    /*public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Plane> getPlaneList() {
        return planeList;
    }*/

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", capacity=" + capacity +
                ", accessibleAirports=" + accessibleAirportCodes +
                ", planeList=" + planeList +
                '}';
    }
}
