package service.dao;

import model.Artefact;
import model.Hero;
import model.enums.HeroClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static model.Utils.jdbcTemplate;

public class HeroDao {
    public void saveHeroes(Collection<Hero> heroes) throws SQLException {
        Hero[] heroArray = (Hero[]) heroes.toArray();
        long[] heroIds = new long[heroArray.length];
        jdbcTemplate.preparedStatement(
                "INSERT INTO hero (name, level, experience, attack, defense, hp) VALUES (?, ?, ?, ?, ?, ?)",
                statement -> {
                    for (Hero hero : heroArray) {
                        statement.setString(1, hero.getName());
                        statement.setInt(2, hero.getLevel());
                        statement.setDouble(3, hero.getExperience());
                        statement.setDouble(4, hero.getAttack());
                        statement.setDouble(5, hero.getDefense());
                        statement.setDouble(6, hero.getHp());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        int i = 0;
                        while (generatedKeys.next()) {
                            long id = generatedKeys.getLong(1);
                            heroIds[i] = id;
                            System.out.println("Saved hero '" + heroArray[i] + "' with the following id: " + id);
                            i++;
                        }
                    }
                }
        );

        jdbcTemplate.preparedStatement(
                "INSERT INTO artefact (type, attack, defense, hp, hero_id) VALUES (?, ?, ?, ?, ?)",
                statement -> {
                    int i = 0;
                    for (Hero hero : heroes) {
                        for (Artefact artefact : hero.getArtefacts()) {
                            statement.setString(1, artefact.getArtefactType().name());
                            statement.setDouble(2, hero.getAttack());
                            statement.setDouble(3, hero.getDefense());
                            statement.setDouble(4, hero.getHp());
                            statement.setLong(5, heroIds[i]);
                            statement.addBatch();
                        }
                        i++;
                    }
                    statement.executeBatch();
                }
        );
    }

    public ArrayList<Hero> getHeroesByIds(ArrayList<Integer> ids) throws SQLException {
        String str = String.format("%0" + ids.size() + "d", 0).replace("0", "?, ").replace("?, $", "");
        return jdbcTemplate.preparedStatement(
                "SELECT * FROM hero h LEFT JOIN artefact a ON h.id=a.hero_id WHERE h.id IN (" + str + ")",
                statement -> {
                    for (int i = 1; i <= ids.size(); i++) {
                        statement.setLong(i, ids.get(i - 1));
                    }
                    ResultSet rs = statement.executeQuery();
                    ArrayList<Hero> heroList = new ArrayList<>();
                    while (rs.next()) {
                        heroList.add(createHero(rs));
                    }
                    return heroList;
                }
        );
    }

    private Hero createHero(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        HeroClass heroClass = HeroClass.getByName(name);

        Hero hero = new Hero.Builder().setHeroClass(heroClass).build();
        hero.setLevel(rs.getInt("level")).setExperience(rs.getDouble("experience"))
                .setHp(rs.getDouble("hp"));
        return hero;
    }
}
