package controller;

import model.Coordinates;
import model.Hero;
import model.Map;
import model.enums.Color;
import model.enums.HeroClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Console;
import java.util.Scanner;

import static model.Utils.getColorString;

public class Game extends JPanel implements ActionListener {
    private static final int MODULO = 3;

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
        int heroNum = -1;
        while (heroNum <= 0 || heroNum > heroClasses.length) {
            System.out.println(getColorString("Choose a hero: ", Color.CYAN));
            heroNum = readInt(scanner);
        }
        return Hero.createHero(heroClasses[heroNum - 1]);
    }

    public static Hero selectPreviousHero(Scanner scanner) {
        // TODO: write method
        return null;
    }

    public static int readInt(Scanner scanner) {
        int returnInt = -1;
        String line = nextLine(scanner);
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
            System.exit(0);
        }
        return line;
    }

    public static void main(String[] args) {
        Console console = System.console();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(getColorString(
                    "What do you want:\n1. Create a hero\n2. Select a previously created hero", Color.CYAN));
            int result = -1;
            while (result <= 0 || result > 2) {
                result = readInt(scanner);
            }
            Hero hero;
            if (result == 1) {
                hero = createHero(scanner);
            } else {
                hero = selectPreviousHero(scanner);
            }
            System.out.println(getColorString("Chosen hero: " + hero, Color.CYAN));
            Map map = createRandomMap(hero);
            int size = map.getSize();
            Coordinates heroCoordinates = new Coordinates(size / 2, size / 2);

//            addKeyListener(new TAdapter());
            while (true) {
                map.printMap(heroCoordinates);
                System.out.println(getColorString(
                        "Where do you want to move:\n1. North (w)\n2. West (a)\n3. East (d)\n4. South (s)",
                        Color.CYAN));
                String line = nextLine(scanner);
            }
        }
    }

    public static void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        System.out.println("key: " + key);
        switch (key) {
            case KeyEvent.VK_LEFT:
                //
            case KeyEvent.VK_RIGHT:
                //
            case KeyEvent.VK_UP:
                //
            case KeyEvent.VK_DOWN:
                //
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    private static class TAdapter extends KeyAdapter {
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            spaceShip.keyReleased(e);
//        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed(e);
        }
    }
}
