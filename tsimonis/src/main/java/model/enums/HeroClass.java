package model.enums;

import com.google.common.collect.Sets;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import model.Artefact;
import model.Utils;
import model.annotations.NotContainsNull;

import java.util.*;

public enum HeroClass {
    WIZARD("Gandalf", 150, 150, Sets.newHashSet(new Artefact(ArtefactType.ARMOR, 50))),
    BALROG("Durin's Bane", 300, 300, Sets.newHashSet(new Artefact(ArtefactType.HELM, 50))),
    DWARF("Thorin Oakenshield", 75, 150, Sets.newHashSet(new Artefact(ArtefactType.WEAPON, 75))),
    ELF("Galadriel", 150, 75, new HashSet<Artefact>()),
    MAN("Aragorn", 75, 75, Sets.newHashSet(
            new Artefact(ArtefactType.ARMOR, 25), new Artefact(ArtefactType.HELM, 25)
    )),
    ENT("Treebeard", 35, 50, Sets.newHashSet(new Artefact(ArtefactType.WEAPON, 25))),
    HOBBIT("Bilbo Baggins", 15, 25, Sets.newHashSet(new Artefact(ArtefactType.HELM, 25))),
    ORC_GOBLIN("The Great Goblin", 50, 25, Sets.newHashSet(new Artefact(ArtefactType.ARMOR, 25))),
    TROLL("Tom", 60, 25, new HashSet<Artefact>()),
    BARROW_WIGHT("Barrow-wight", 100, 250, Sets.newHashSet(
            new Artefact(ArtefactType.WEAPON, 50), new Artefact(ArtefactType.ARMOR, 75)
    )),
    TOM_BOMBADIL("Tom Bombadil", 500, 500, Sets.newHashSet(
            new Artefact(ArtefactType.HELM, 40), new Artefact(ArtefactType.ARMOR, 50),
            new Artefact(ArtefactType.WEAPON, 60)
    )),
    RIVER_SPIRIT("Goldberry", 80, 50, Sets.newHashSet(new Artefact(ArtefactType.WEAPON, 20))),
    GIANT("Bargrisar", 75, 50, Sets.newHashSet(
            new Artefact(ArtefactType.ARMOR, 50), new Artefact(ArtefactType.HELM, 30)
    )),
    DRAGON("Smaug", 500, 250, new HashSet<Artefact>()),
    BIRD("The Lord of the Eagles", 200, 200, Sets.newHashSet(
            new Artefact(ArtefactType.ARMOR, 10), new Artefact(ArtefactType.HELM, 40)
    )),
    Maia("Sauron", 1000, 1000, Sets.newHashSet(new Artefact(ArtefactType.WEAPON, 65)));

    private static final List<HeroClass> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    @NotEmpty
    private final String name;

    @Min(0)
    @Max(1000)
    private final double attack;

    @Min(0)
    @Max(1000)
    private final double defence;

    @NotNull
    @NotContainsNull
    private final Set<Artefact> artefacts;

    private static final Map<String, HeroClass> BY_NAME = new HashMap<>();

    static {
        for (HeroClass heroclass : values()) {
            BY_NAME.put(heroclass.name, heroclass);
        }
    }

    public static HeroClass getByName(String name) {
        return BY_NAME.get(name);
    }

    HeroClass(String name, double attack, double defence, Set<Artefact> artefacts) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.artefacts = artefacts;
        Utils.validate(this);
    }

    public static HeroClass getRandomHeroClass() {
        return VALUES.get(Utils.RANDOM.nextInt(SIZE));
    }

    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    public Set<Artefact> getArtefacts() {
        return artefacts;
    }

    @Override
    public String toString() {
        return "HeroClass{" +
                "race='" + this.name() + '\'' +
                ", name='" + name + '\'' +
                ", attack=" + attack +
                ", defence=" + defence +
                ", artefacts=" + artefacts +
                '}';
    }
}
