package model;

import java.util.Set;

public enum HeroClass {
    WIZARD("Gandalf", 150, 150, ),
    BALROG("Durin's Bane", 300, 300),
    DWARF("Thorin Oakenshield", 75, 150),
    ELF("Galadriel", 150, 75),
    MAN("Aragorn", 75, 75),
    ENT("Treebeard", 35, 50),
    HOBBIT("Bilbo Baggins", 15, 25),
    ORC_GOBLIN("The Great Goblin", 50, 25),
    TROLL("Tom", 60, 25),
    BARROW_WIGHT("Barrow-wight", 100, 250),
    TOM_BOMBADIL("Tom Bombadil", 500, 500),
    RIVER_SPIRIT("Goldberry", 80, 50),
    GIANT("Bargrisar", 75, 50),
    DRAGON("Smaug", 500, 250),
    BIRD("The Lord of the Eagles", 200, 200),
    Maia("Sauron", 1000, 1000);

    private final String name;
    private final double attack;
    private final double defence;
    private final Set<Artefact> artefacts;

    HeroClass(String name, double attack, double defence) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.artefacts = artefacts;
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
}
