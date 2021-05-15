package service.dao;

import model.Artefact;
import model.Hero;
import model.enums.ArtefactType;
import model.enums.HeroClass;
import model.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static model.Utils.jdbcTemplate;

public class HeroDao {
    public void saveHero(Hero hero) throws SQLException {
        Long heroId = jdbcTemplate.preparedStatement(
                "INSERT INTO hero (name, level, hp) VALUES (?, ?, ?)",
                statement -> {
                    statement.setString(1, hero.getName());
                    statement.setInt(2, hero.getLevel());
                    statement.setDouble(3, hero.getHp());
                    statement.execute();
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        long innerHeroId = 0;
                        if (generatedKeys.next()) {
                            innerHeroId = generatedKeys.getLong(1);
                            System.out.println("Saved hero (id='" + innerHeroId + "') " + hero + "' with the following id: " + innerHeroId);
                        }
                        return innerHeroId;
                    }
                }
        );

        jdbcTemplate.preparedStatement(
                "INSERT INTO artefact (type, value, hero_id) VALUES (?, ?, ?)",
                statement -> {
                    for (Artefact artefact : hero.getArtefacts()) {
                        statement.setString(1, artefact.getArtefactType().name());
                        statement.setDouble(2, artefact.getValue());
                        statement.setLong(3, heroId);
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
        );
    }

    public void updateHero(Hero hero) throws SQLException {
        Long heroId = jdbcTemplate.preparedStatement(
                "UPDATE hero SET name=?, level=?, hp=? WHERE id=?",
                statement -> {
                    statement.setString(1, hero.getName());
                    statement.setInt(2, hero.getLevel());
                    statement.setDouble(3, hero.getHp());
                    statement.setLong(4, hero.getDbId());
                    statement.execute();
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        Long innerHeroId = null;
                        if (generatedKeys.next()) {
                            innerHeroId = generatedKeys.getLong(1);
                            System.out.println("Updated hero (id='" + innerHeroId + "') " + hero + "' with the following id: " + innerHeroId);
                        }
                        return innerHeroId;
                    }
                }
        );

        if (heroId == null) {
            throw new DatabaseException("Update was unsuccessful");
        }
        dropConnectedArtefacts(heroId);

        jdbcTemplate.preparedStatement(
                "INSERT INTO artefact (type, value, hero_id) VALUES (?, ?, ?)",
                statement -> {
                    for (Artefact artefact : hero.getArtefacts()) {
                        statement.setString(1, artefact.getArtefactType().name());
                        statement.setDouble(2, artefact.getValue());
                        statement.setLong(3, heroId);
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
        );
    }

    public void saveHeroes(Collection<Hero> heroes) throws SQLException {
        Hero[] heroArray = (Hero[]) heroes.toArray();
        long[] heroIds = new long[heroArray.length];
        jdbcTemplate.preparedStatement(
                "INSERT INTO hero (name, level, hp) VALUES (?, ?, ?)",
                statement -> {
                    for (Hero hero : heroArray) {
                        statement.setString(1, hero.getName());
                        statement.setInt(2, hero.getLevel());
                        statement.setDouble(3, hero.getHp());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        int i = 0;
                        while (generatedKeys.next()) {
                            long id = generatedKeys.getLong(1);
                            heroIds[i] = id;
                            System.out.println(
                                    "Saved hero (id='" + id + "') " + heroArray[i] + "' with the following id: " + id);
                            i++;
                        }
                    }
                }
        );

        jdbcTemplate.preparedStatement(
                "INSERT INTO artefact (type, value, hero_id) VALUES (?, ?, ?)",
                statement -> {
                    int i = 0;
                    for (Hero hero : heroes) {
                        for (Artefact artefact : hero.getArtefacts()) {
                            statement.setString(1, artefact.getArtefactType().name());
                            statement.setDouble(2, artefact.getValue());
                            statement.setLong(3, heroIds[i]);
                            statement.addBatch();
                        }
                        i++;
                    }
                    statement.executeBatch();
                }
        );
    }

    private void dropConnectedArtefacts(Long heroId) throws SQLException {
        jdbcTemplate.preparedStatement(
                "DELETE * FROM artefact WHERE hero_id=?",
                statement -> {
                    statement.setLong(1, heroId);
                    statement.execute();
                }
        );
    }

    public Hero getHeroById(int id) throws SQLException {
        return jdbcTemplate.preparedStatement(
                "SELECT * FROM hero h LEFT JOIN artefact a ON h.id=a.hero_id WHERE h.id=?",
                statement -> {
                    statement.setLong(1, id);
                    ResultSet rs = statement.executeQuery();
                    return createHero(rs);
                }
        );
    }

    public ArrayList<Hero> getHeroesByIds(ArrayList<Integer> ids) throws SQLException {
        ArrayList<Hero> heroList = new ArrayList<>();
        for (int id : ids) {
            heroList.add(getHeroById(id));
        }
        return heroList;
    }

    private Hero createHero(ResultSet rs) throws SQLException {
        Hero hero = null;
        Set<Artefact> artefacts = new HashSet<>();
        while (rs.next()) {
            if (hero == null) {
                String name = rs.getString("name");
                HeroClass heroClass = HeroClass.getByName(name);

                hero = new Hero.Builder().setHeroClass(heroClass).build();
                hero.setLevel(rs.getInt("level")).setHp(rs.getDouble("hp")).setDbId(rs.getInt("id"));
            }
            ArtefactType artefactType = ArtefactType.valueOf(rs.getString("type"));
            Artefact artefact = new Artefact(artefactType, rs.getDouble("value"));
            artefacts.add(artefact);
        }
        if (hero == null) {
            throw new DatabaseException("Incorrect ResultSet received...");
        }
        return hero.setArtefacts(artefacts);
    }
}
