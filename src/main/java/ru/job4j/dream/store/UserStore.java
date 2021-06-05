package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.dream.db.ConnectionPool;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserStore implements Store<User> {

    private static final Logger LOG = LogManager.getLogger(PsqlPostStore.class.getName());

    private final BasicDataSource pool = new ConnectionPool().getInstance();

    private static final class Lazy {
        private static final Store<User> INST = new UserStore();
    }

    public static Store<User> instOf() {
        return UserStore.Lazy.INST;
    }

    @Override
    public void save(User user) {
        create(user);
    }

    private void create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO users(name, email, password) VALUES (?, ?, ?);",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Create exception", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email =?;",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Find by email exception", e);
        }
        return user;
    }
}
