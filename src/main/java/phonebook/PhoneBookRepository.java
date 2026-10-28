package phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class PhoneBookRepository implements AutoCloseable {

    private final Connection connection;

    public PhoneBookRepository(String jdbcUrl) {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            try (Statement st = connection.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS phone_book (name VARCHAR(255) PRIMARY KEY, phone VARCHAR(50) NOT NULL)");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Не удалось открыть справочник", e);
        }
    }

    /** Сохраняет номер; существующее имя перезаписывается. */
    public void save(String name, String phone) {
        try (PreparedStatement ps = connection.prepareStatement("MERGE INTO phone_book KEY(name) VALUES (?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Не удалось сохранить запись", e);
        }
    }

    public Optional<String> findPhone(String name) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT phone FROM phone_book WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(rs.getString(1)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Не удалось прочитать запись", e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
