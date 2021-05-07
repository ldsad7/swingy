package model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import model.annotations.NotContainsNull;
import model.enums.HeroClass;
import model.exceptions.HeroException;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Hero {
    private static final Random RANDOM = new Random();
    private static long idCounter;

    @Min(1)
    private long id;

    @NotNull
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    private HeroClass heroClass;

    @Min(1)
    private int level = 1;

    @Min(0)
    private double experience = 0;

    @Min(0)
    private double attack;

    @Min(0)
    private double defense;

    @Min(0)
    @Max(100)
    private double hp = 100;

    @NotNull
    @NotContainsNull
    private Set<Artefact> artefacts;

    private Hero() {
    }

    public static Hero getRandomHero() {
        HeroClass heroClass = HeroClass.getRandomHeroClass();
        Hero hero = new Hero.Builder().setHeroClass(heroClass).build();
        double experience = 1000 * RANDOM.nextDouble();
        hero.setExperience(experience);
        return hero;
    }

    private long nextId() {
        return ++idCounter;
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

    public Hero setExperience(double experience) {
        this.experience = experience;
        Utils.validate(this);
        return this;
    }

    public double getAttack() {
        return attack;
    }

    public Hero setAttack(double attack) {
        this.attack = attack;
        Utils.validate(this);
        return this;
    }

    public double getDefense() {
        return defense;
    }

    public Hero setDefense(double defense) {
        this.defense = defense;
        Utils.validate(this);
        return this;
    }

    public double getHp() {
        return hp;
    }

    public Hero setHp(double hp) {
        this.hp = hp;
        Utils.validate(this);
        return this;
    }

    public Set<Artefact> getArtefacts() {
        return artefacts;
    }

    public Hero setArtefacts(Set<Artefact> artefacts) {
        this.artefacts = artefacts;
        Utils.validate(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return id == hero.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", heroClass=" + heroClass +
                ", level=" + level +
                ", experience=" + experience +
                ", attack=" + attack +
                ", defense=" + defense +
                ", hp=" + hp +
                ", artefacts=" + artefacts +
                '}';
    }

    public static class Builder {
        private final Hero hero;

        public Builder() {
            hero = new Hero();
        }

        public Builder setHeroClass(HeroClass heroClass) {
            if (heroClass == null) {
                throw new HeroException("Given heroClass is null");
            }
            hero.name = heroClass.getName();
            hero.attack = heroClass.getAttack();
            hero.defense = heroClass.getDefence();
            hero.artefacts = heroClass.getArtefacts();
            hero.heroClass = heroClass;
            return this;
        }

        public Builder setLevel(int level) {
            hero.level = level;
            return this;
        }

        public Hero build() {
            hero.id = hero.nextId();
            Utils.validate(hero);
            return hero;
        }
    }
}
