package model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import model.annotations.NotContainsNull;
import model.enums.HeroClass;
import model.exceptions.HeroException;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Hero {
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
    private double attack;

    @Min(0)
    private double defense;

    @Min(0)
    @Max(100)
    private double hp = 100;

    @NotNull
    @NotContainsNull
    private Set<Artefact> artefacts;

    @Min(1)
    private Integer dbId;

    private Hero() {
    }

    public static Hero getRandomHero() {
        return createHero(HeroClass.getRandomHeroClass());
    }

    public static Hero createHero(HeroClass heroClass) {
        return new Hero.Builder().setHeroClass(heroClass).build();
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

    public Hero setLevel(int level) {
        this.level = level;
        Utils.validate(this);
        return this;
    }

    public double getExperience() {
        return level * 1000 + (level - 1) * (level - 1) * 450;
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

    public Hero setHp(double hp) {
        this.hp = hp;
        Utils.validate(this);
        return this;
    }

    public Set<Artefact> getArtefacts() {
        return Collections.unmodifiableSet(artefacts);
    }

    public Hero setArtefacts(Set<Artefact> artefacts) {
        this.artefacts = artefacts;
        Utils.validate(this);
        return this;
    }

    public Hero addArtefact(Artefact artefact) {
        this.artefacts.add(artefact);
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
                ", experience=" + getExperience() +
                ", attack=" + attack +
                ", defense=" + defense +
                ", hp=" + hp +
                ", artefacts=" + artefacts +
                '}';
    }

    public String prettyString() {
        StringBuilder result = new StringBuilder(String.format(
                "Hero %16s | level %5s | exeprience %12s | hp %8s | attack %8s | defense %8s |\n" +
                "%-21s | %-11d | %-23f | %-11f | %-15f | %-16f |\n",
                "", "", "", "", "", "", name, level, getExperience(), hp, attack, defense
        ));
        if (!artefacts.isEmpty()) {
            result.append("Artefacts: \n").append("field    | value     |\n");
            for (Artefact artefact : artefacts) {
                result.append(artefact.prettyString()).append("\n");
            }
        }
        return result.toString();
    }

    public Integer getDbId() {
        return dbId;
    }

    public Hero setDbId(Integer dbId) {
        this.dbId = dbId;
        Utils.validate(this);
        return this;
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

        public Hero build() {
            hero.id = hero.nextId();
            Utils.validate(hero);
            return hero;
        }
    }
}
