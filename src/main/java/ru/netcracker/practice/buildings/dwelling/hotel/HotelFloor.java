package ru.netcracker.practice.buildings.dwelling.hotel;

import ru.netcracker.practice.buildings.dwelling.DwellingFloor;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.util.Arrays;
import java.util.Objects;

public class HotelFloor extends DwellingFloor {
    private static final int STARS_DEFAULT = 1;
    private int stars;

    public HotelFloor() {
        super();
        this.stars = STARS_DEFAULT;
    }

    public HotelFloor(int flatsNumber) {
        super(flatsNumber);
        this.stars = STARS_DEFAULT;
    }

    public HotelFloor(Space[] flats) {
        super(flats);
        this.stars = STARS_DEFAULT;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public float getCoeff() {
        float coeff;
        switch (stars) {
            case (2):
                coeff = 0.5f;
                break;
            case (3):
                coeff = 1.0f;
                break;
            case (4):
                coeff = 1.25f;
                break;
            case (5):
                coeff = 1.5f;
                break;
            default:
                coeff = 0.25f;
                break;
        }
        return coeff;
    }

    @Override
    public String toString() {
        return super.toString().replace("DwellingFLoor", "HotelFloor " + getStars() + ",");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelFloor)) return false;
        HotelFloor that = (HotelFloor) o;
        return getTotalSpaces() == that.getTotalSpaces() &&
                getStars() == that.getStars() &&
                Arrays.equals(getSpacesAsArray(), that.getSpacesAsArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stars);
    }
}
