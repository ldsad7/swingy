package model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import model.annotations.NotContainsNull;
import model.enums.Color;
import model.enums.Direction;
import model.exceptions.MapException;

import java.util.Arrays;

import static model.Utils.getColorString;

public class Map {
    @Min(1)
    private final int size;

    @NotNull
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

    public Coordinates moveHero(Coordinates coordinates, Direction direction) {
        Coordinates newCoordinates = direction.getCoordinates();
        if (getHero(newCoordinates) != null) {
            throw new MapException("Can't move hero to a new position {" + newCoordinates + "}, " +
                    "because there is already a hero");
        }
        setHero(getHero(coordinates), newCoordinates);
        setHero(null, coordinates);
        Utils.validate(this);
        return newCoordinates;
    }

    public int getSize() {
        return size;
    }

    public Hero[][] getMap() {
        return map;
    }

    public void printMap(Coordinates heroCoordinates) {
        int x = heroCoordinates.getX();
        int y = heroCoordinates.getY();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (y == i && x == j) {
                    stringBuilder.append(getColorString("[H]", Color.RED));
                } else if (map[i][j] == null) {
                    stringBuilder.append(getColorString("[O]", Color.BOLD));
                } else {
                    stringBuilder.append(getColorString("[H]", Color.BLUE));
                }
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "Map{" +
                "size=" + size +
                ", map=" + Arrays.toString(map) +
                '}';
    }
}
