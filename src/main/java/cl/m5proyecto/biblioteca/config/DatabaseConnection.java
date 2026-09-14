package cl.m5proyecto.biblioteca.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    private final Properties properties = new Properties();

    private DatabaseConnection() {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("No se encontró db.properties");
            }
            properties.load(input);
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException("No fue posible cargar la configuración de base de datos", exception);
        }
    }

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.username"), properties.getProperty("db.password"));
    }
}
