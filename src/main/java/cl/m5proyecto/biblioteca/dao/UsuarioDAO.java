package cl.m5proyecto.biblioteca.dao;

import cl.m5proyecto.biblioteca.config.DatabaseConnection;
import cl.m5proyecto.biblioteca.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public Usuario validarLogin(String email, String password) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, rol, activo FROM usuarios WHERE email = ? AND password = ?";
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

    public List<Usuario> obtenerUsuarios() throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, rol, activo FROM usuarios WHERE rol = ? ORDER BY nombre ASC";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "USUARIO");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    usuarios.add(new Usuario(
                            result.getInt("id_usuario"),
                            result.getString("nombre"),
                            result.getString("email"),
                            result.getString("rol"),
                            result.getBoolean("activo")
                    ));
                }
            }
        }
        return usuarios;
    }

    public boolean actualizarEstadoUsuario(int idUsuario, boolean activo) throws SQLException {
        String sql = "UPDATE usuarios SET activo = ? WHERE id_usuario = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, activo);
            statement.setInt(2, idUsuario);
            return statement.executeUpdate() == 1;
        }
    }
}
