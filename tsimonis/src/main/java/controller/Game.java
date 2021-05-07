package controller;

import model.Coordinates;
import model.Hero;
import model.Map;

public class Game {
    public Map createRandomMap(Hero hero) {
        Map map = new Map(hero.getLevel());
        int size = map.getSize();
        for (int i = 0; i < size / 3; i++) {
            Hero villain = Hero.getRandomHero();
            Coordinates coordinates = Coordinates.getRandomCoordinates(size);
            map.setHero(villain, coordinates);
        }
        return map;
    }

}
