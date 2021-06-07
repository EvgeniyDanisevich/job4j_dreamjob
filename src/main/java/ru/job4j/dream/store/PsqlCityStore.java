package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.dream.db.ConnectionPool;
import ru.job4j.dream.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCityStore implements Store<City> {

    private static final Logger LOG = LogManager.getLogger(PsqlCandidateStore.class.getName());

    private final BasicDataSource pool = new ConnectionPool().getInstance();

    private static final class Lazy {
        private static final Store<City> INST = new PsqlCityStore();
    }

    public static Store<City> instOf() {
        return PsqlCityStore.Lazy.INST;
    }

    @Override
    public Collection<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(
                                    it.getInt("id"),
                                    it.getString("name")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Find all exception", e);
        }
        return cities;
    }

    @Override
    public City findById(int id) {
        City city = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM city WHERE id=?;",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    city = new City(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Find by id exception", e);
        }
        return city;
    }
}
