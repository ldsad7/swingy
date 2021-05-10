package model.enums;

import jakarta.validation.constraints.NotEmpty;
import model.Artefact;
import model.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ArtefactType {
    WEAPON("attack"),
    ARMOR("defense"),
    HELM("hp");

    @NotEmpty
    private final String field;

    private static final List<ArtefactType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    ArtefactType(String field) {
        this.field = field;
        Utils.validate(this);
    }

    public static ArtefactType getRandomArtefactType() {
        return VALUES.get(Utils.RANDOM.nextInt(SIZE));

    }

    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        return "ArtefactType{" +
                "field='" + field + '\'' +
                '}';
    }
}
