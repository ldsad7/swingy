package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import model.annotations.NotContainsNull;
import model.enums.Direction;
import model.exceptions.MapException;

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
        int currX = coordinates.getX();
        int currY = coordinates.getY();
        Coordinates currCoordinates = direction.getCoordinates();
        int newX = currX + currCoordinates.getX();
        int newY = currY + currCoordinates.getY();
        if (newX < 0 || newX >= this.size || newY < 0 || newY >= this.size ||
                currX < 0 || currX >= this.size || currY < 0 || currY >= this.size) {
            throw new MapException("Can't move hero to a new position {" + newX + ", " + newY + "}, " +
                    "because coordinates are incorrect");
        }
        if (this.map[newX][newY] != null) {
            throw new MapException("Can't move hero to a new position {" + newX + ", " + newY + "}, " +
                    "because there is already a hero");
        }
        this.map[newY][newX] = this.map[currY][currX];
        this.map[currY][currX] = null;
        Utils.validate(this);
    }

    public int getSize() {
        return size;
    }

    public Hero[][] getMap() {
        return map;
    }
}
