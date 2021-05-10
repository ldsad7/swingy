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
    private double value;

    public Artefact(ArtefactType artefactType, double value) {
        this.artefactType = artefactType;
        this.value = value;
        this.id = nextId();
        Utils.validate(this);
    }

    public static Artefact getRandomArtefact() {
        return new Artefact(ArtefactType.getRandomArtefactType(), 1000 * Utils.RANDOM.nextDouble());
    }

    private long nextId() {
        return ++idCounter;
    }

    public double getValue() {
        return value;
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
                "id=" + id +
                ", artefactType=" + artefactType +
                ", value=" + value +
                '}';
    }
}
