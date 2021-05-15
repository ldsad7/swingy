package controller;

import model.Coordinates;
import model.Hero;
import model.Map;
import model.enums.Color;
import model.enums.Direction;
import model.enums.HeroClass;
import model.exceptions.DatabaseException;
import service.dao.HeroDao;
import service.db.DbInit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static model.Utils.getColorString;
import static model.Utils.jdbcTemplate;

public class Game {
    private static final int MODULO = 3;
    private static final HeroDao heroDao = new HeroDao();

    public static Map createRandomMap(Hero hero) {
        Map map = new Map(hero.getLevel());
        int size = map.getSize();
        for (int i = 0; i < (size * size) / MODULO; i++) {
            Hero villain = Hero.getRandomHero();
            Coordinates coordinates = Coordinates.getRandomCoordinates(size);
            map.setHero(villain, coordinates);
        }
        return map;
    }

    public static Hero createHero(Scanner scanner) {
        HeroClass[] heroClasses = HeroClass.values();
        System.out.println(Color.GREEN.getColor());
        for (int i = 1; i <= heroClasses.length; i++) {
            System.out.println(i + ". " + heroClasses[i - 1]);
        }
        System.out.println(Color.ENDC.getColor());
        Integer heroNum = -1;
        while (heroNum <= 0 || heroNum > heroClasses.length) {
            System.out.println(getColorString("Choose a hero: ", Color.CYAN));
            heroNum = readInt(scanner);
            if (heroNum == null) {
                return null;
            }
        }
        return Hero.createHero(heroClasses[heroNum - 1]);
    }

    public static Hero selectPreviousHero(Scanner scanner) {
        System.out.println(getColorString("Enter hero's database id: ", Color.BOLD));
        Hero hero;
        while (true) {
            Integer id = readInt(scanner);
            if (id == null) {
                return null;
            }
            if (id >= 0) {
                try {
                    hero = heroDao.getHeroById(id);
                    break;
                } catch (SQLException | DatabaseException e) {
                    System.out.println(getColorString(
                            "There is no such id in the database, try again. If you want to exit, enter `exit`", Color.RED));
                }
            }
        }
        return hero;
    }

    public static Integer readInt(Scanner scanner) {
        int returnInt = -1;
        String line = nextLine(scanner);
        if (line == null) {
            return null;
        }
        try {
            returnInt = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println(getColorString("Incorrect number '" + line + "' received", Color.RED));
        }
        return returnInt;
    }

    public static String nextLine(Scanner scanner) {
        String line = scanner.nextLine();
        if (line.matches("^exit|quit|q$")) {
            return null;
        }
        return line;
    }

    public static void main(String[] args) {
        try {
            new DbInit(jdbcTemplate).create();
        } catch (SQLException | IOException e) {
            throw new DatabaseException("We weren't able to init database (" + e + ")");
        }

        HeroDao heroDao = new HeroDao();
        Hero hero = null;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(getColorString(
                    "What do you want:\n1. Create a hero\n2. Select a previously created hero", Color.CYAN));
            Integer result = -1;
            while (result <= 0 || result > 2) {
                result = readInt(scanner);
                if (result == null) {
                    return;
                }
            }

            if (result == 1) {
                hero = createHero(scanner);
            } else {
                hero = selectPreviousHero(scanner);
            }
            if (hero == null) {
                return;
            }
            System.out.println(getColorString("Chosen hero: " + hero, Color.CYAN));

            Map map = createRandomMap(hero);
            int size = map.getSize();
            Coordinates heroCoordinates = new Coordinates(size / 2, size / 2);
            System.out.println(getColorString(
                    "Moves in the map:\n1. North (w/W)\n2. West (a/A)\n3. East (d/D)\n4. South (s/S)",
                    Color.CYAN));
            while (true) {
                // TODO: try in school (http://braun-home.net/michael/info/misc/VT100_commands.htm#:~:text=6%20l%20turn%20off%20region,position%20%2D%20pl%20Line%2C%20pc%20Column)
                // https://stackoverflow.com/questions/15051688/is-it-possible-to-rewrite-previous-line-in-console
//                System.out.print("\033[1J");
                System.out.println(hero.prettyString());
                map.printMap(heroCoordinates);
                String line = nextLine(scanner);
                if (line == null) {
                    return;
                }
                Direction direction = null;
                switch (line.trim().toLowerCase()) {
                    case "w":
                        direction = Direction.NORTH;
                        break;
                    case "s":
                        direction = Direction.SOUTH;
                        break;
                    case "a":
                        direction = Direction.WEST;
                        break;
                    case "d":
                        direction = Direction.EAST;
                        break;
                    default:
                        System.out.println("Received incorrect value, should be one of [WwAaSsDd]");
                }
                if (direction != null) {
                    Coordinates newCoordinates = heroCoordinates.addDirectionToCoordinates(direction);
                    if (!newCoordinates.checkCoordinates(size)) {
                        System.out.println(getColorString("Well done! Try another level :)", Color.GREEN));
                        hero.setLevel(hero.getLevel() + 1);

                        map = createRandomMap(hero);
                        size = map.getSize();
                        heroCoordinates = new Coordinates(size / 2, size / 2);
                        continue;
                    }
                    heroCoordinates = map.moveHero(heroCoordinates, newCoordinates);
                    if (heroCoordinates == null) {
                        System.out.println(getColorString("Unfortunately you lost...", Color.RED));
                        return;
                    }
                }
            }
        } finally {
            if (hero != null) {
                try {
                    if (hero.getDbId() != null) {
                        heroDao.updateHero(hero);
                    } else {
                        heroDao.saveHero(hero);
                    }
                } catch (SQLException e) {
                    System.out.println("We weren't able to save the hero `" + hero + "` to the database (" + e + ")");
                }
            }
        }
    }
}
