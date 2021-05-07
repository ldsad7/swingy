package model.enums;

import jakarta.validation.constraints.NotNull;
import model.Coordinates;
import model.Utils;

public enum Direction {
    NORTH(new Coordinates(0, -1)),
    EAST(new Coordinates(1, 0)),
    SOUTH(new Coordinates(0, 1)),
    WEST(new Coordinates(-1, 0));

    @NotNull
    private final Coordinates coordinates;

    Direction(Coordinates coordinates) {
        this.coordinates = coordinates;
        Utils.validate(this);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
