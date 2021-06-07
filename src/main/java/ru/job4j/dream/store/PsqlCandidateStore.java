package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.dream.db.ConnectionPool;
import ru.job4j.dream.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCandidateStore implements Store<Candidate> {

    private static final Logger LOG = LogManager.getLogger(PsqlCandidateStore.class.getName());

    private final BasicDataSource pool = new ConnectionPool().getInstance();

    private static final class Lazy {
        private static final Store<Candidate> INST = new PsqlCandidateStore();
    }

    public static Store<Candidate> instOf() {
        return PsqlCandidateStore.Lazy.INST;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("photo"),
                                    it.getInt("city_id")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Find all exception", e);
        }
        return candidates;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private void create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO candidate(name) VALUES (?)",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Create exception", e);
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name=? WHERE id=?;",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Update exception", e);
        }
    }

    @Override
    public Candidate findById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate WHERE id=?;",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    candidate = new Candidate(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("photo"),
                            rs.getInt("city_id")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Find by id exception", e);
        }
        return candidate;
    }

    @Override
    public void delete(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM candidate WHERE id=?;",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Delete exception", e);
        }
    }
}
