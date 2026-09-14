package cl.m5proyecto.biblioteca.dao;

import cl.m5proyecto.biblioteca.config.DatabaseConnection;
import cl.m5proyecto.biblioteca.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public Usuario validarLogin(String email, String password) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, rol, activo FROM usuarios WHERE email = ? AND password = ? AND activo = true";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Usuario(result.getInt("id_usuario"), result.getString("nombre"),
                            result.getString("email"), result.getString("rol"), result.getBoolean("activo"));
                }
            }
        }
        return null;
    }
}
