package fr.iut.model;

import javafx.geometry.Point2D;

/**
 * Created by shellcode on 2/9/17.
 */
public class Location {
    private String name;
    private int capacity;

    private boolean hasElectricity;
    private boolean hasWater;
    private boolean hasShadow;
    private Point2D mapPosition;

    public Location(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public void store() {
        //TODO : query DB to save the location
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int value) {
        capacity = value;
    }

    public boolean hasElectricity() {
        return hasElectricity;
    }

    public boolean hasWater() {
        return hasWater;
    }

    public boolean hasShadow() {
        return hasShadow;
    }

    public void setHasElectricity(boolean value) {
        hasElectricity = value;
    }

    public void setHasWater(boolean value) {
        hasWater = value;
    }

    public void setHasShadow(boolean value) {
        hasShadow = value;
    }

    public void setPositionOnMap(double x, double y) {
        mapPosition = new Point2D(x, y);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
