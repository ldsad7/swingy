package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import model.annotations.NotContainsNull;
import model.enums.Direction;
import model.exceptions.MapException;

import java.util.Arrays;

public class Map {
    @Min(1)
    private final int size;

    @NotNull
    @NotContainsNull
    private final Hero[][] map;

    public Map(int level) {
        this.size = (level - 1) * 5 + 10 - (level % 2);
        this.map = new Hero[size][size];
        Utils.validate(this);
    }

    public Hero getHero(Coordinates coordinates) {
        int currX = coordinates.getX();
        int currY = coordinates.getY();
        if (currX < 0 || currX >= this.size || currY < 0 || currY >= this.size) {
            throw new MapException("Can't get hero from the position {" + currX + ", " + currY + "}");
        }
        return this.map[coordinates.getY()][coordinates.getX()];
    }

    public void setHero(Hero hero, Coordinates coordinates) {
        int currX = coordinates.getX();
        int currY = coordinates.getY();
        if (currX < 0 || currX >= this.size || currY < 0 || currY >= this.size) {
            throw new MapException("Can't set hero to the position {" + currX + ", " + currY + "}");
        }
        this.map[coordinates.getY()][coordinates.getX()] = hero;
        Utils.validate(this);
    }

    public void moveHero(Coordinates coordinates, Direction direction) {
        Coordinates newCoordinates = direction.getCoordinates();
        if (getHero(newCoordinates) != null) {
            throw new MapException("Can't move hero to a new position {" + newCoordinates + "}, " +
                    "because there is already a hero");
        }
        setHero(getHero(coordinates), newCoordinates);
        setHero(null, coordinates);
        Utils.validate(this);
    }

    public int getSize() {
        return size;
    }

    public Hero[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stringBuilder.append("[" + "H" + "]");
            }
        }
        return stringBuilder.toString();
    }
}
