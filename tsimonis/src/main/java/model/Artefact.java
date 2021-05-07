package model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import model.enums.ArtefactType;

import java.util.Objects;

public class Artefact {
    private static long idCounter;

    @Min(1)
    private final long id;

    @NotNull
    private final ArtefactType artefactType;

    @Min(0)
    @Max(1000)
    private double attack;

    @Min(0)
    @Max(1000)
    private double defense;

    @Min(0)
    @Max(100)
    private double hp;

    public Artefact(ArtefactType artefactType, double value) {
        switch (artefactType) {
            case WEAPON:
                attack = value;
                break;
            case ARMOR:
                defense = value;
                break;
            case HELM:
                hp = value;
                break;
        }
        this.artefactType = artefactType;
        this.id = nextId();
        Utils.validate(this);
    }

    private long nextId() {
        return ++idCounter;
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

    public ArtefactType getArtefactType() {
        return artefactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artefact artefact = (Artefact) o;
        return id == artefact.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Artefact{" +
                "artefactType=" + artefactType +
                ", id=" + id +
                ", attack=" + attack +
                ", defense=" + defense +
                ", hp=" + hp +
                '}';
    }
}
