package model;

import model.enums.Direction;

import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates getRandomCoordinates(int size) {
        return new Coordinates(Utils.RANDOM.nextInt(size), Utils.RANDOM.nextInt(size));
    }

    public boolean checkCoordinates(int size) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;
        }
        return true;
    }

    public Coordinates addDirectionToCoordinates(Direction direction) {
        Coordinates directionCoordinates = direction.getCoordinates();
        return new Coordinates(
                this.x + directionCoordinates.getX(),
                this.y + directionCoordinates.getY()
        );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
