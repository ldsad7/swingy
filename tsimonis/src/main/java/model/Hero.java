package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Hero {
    private String name;
    private HeroClass heroClass;
    private int level;
    private double experience;
    private double attack;
    private double defense;
    private double hp;

    private Set<Artefact> artefacts;

    public Hero setHero(HeroClass heroClass) {
        this.name = heroClass.getName();
        this.attack = heroClass.getAttack();
        this.defense = heroClass.getDefence();
        return this;
    }



    public String getName() {
        return name;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public int getLevel() {
        return level;
    }

    public double getExperience() {
        return experience;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getHp() {
        return hp;
    }
}
