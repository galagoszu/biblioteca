package cl.m5proyecto.biblioteca.dao;

import cl.m5proyecto.biblioteca.config.DatabaseConnection;
import cl.m5proyecto.biblioteca.model.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private static final String COLUMNS = "id_libro, titulo, autor, isbn, anio_publicacion, genero, disponible";

    public List<Libro> listarTodos(String busqueda) throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM libros WHERE titulo LIKE ? OR autor LIKE ? OR genero LIKE ? ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        String filter = "%" + (busqueda == null ? "" : busqueda.trim()) + "%";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filter);
            statement.setString(2, filter);
            statement.setString(3, filter);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) libros.add(map(result));
            }
        }
        return libros;
    }

    public Libro buscarPorId(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT " + COLUMNS + " FROM libros WHERE id_libro = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) { return result.next() ? map(result) : null; }
        }
    }

    public void crear(Libro libro) throws SQLException {
        executeUpdate("INSERT INTO libros (titulo, autor, isbn, anio_publicacion, genero, disponible) VALUES (?, ?, ?, ?, ?, ?)", libro);
    }

    public void actualizar(Libro libro) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE libros SET titulo=?, autor=?, isbn=?, anio_publicacion=?, genero=? WHERE id_libro=?")) {
            fill(statement, libro); statement.setInt(6, libro.getIdLibro()); statement.executeUpdate();
        }
    }

    public boolean eliminar(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM libros WHERE id_libro = ? AND NOT EXISTS (SELECT 1 FROM prestamos WHERE id_libro = ? AND fecha_devolucion IS NULL)")) {
            statement.setInt(1, id); statement.setInt(2, id); return statement.executeUpdate() > 0;
        }
    }

    public int contar() throws SQLException { return count("SELECT COUNT(*) FROM libros"); }
    public int contarDisponibles() throws SQLException { return count("SELECT COUNT(*) FROM libros WHERE disponible = true"); }

    private void executeUpdate(String sql, Libro libro) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fill(statement, libro); statement.setBoolean(6, libro.isDisponible()); statement.executeUpdate();
        }
    }
    private void fill(PreparedStatement statement, Libro libro) throws SQLException {
        statement.setString(1, libro.getTitulo()); statement.setString(2, libro.getAutor()); statement.setString(3, libro.getIsbn());
        statement.setInt(4, libro.getAnioPublicacion()); statement.setString(5, libro.getGenero());
    }
    private int count(String sql) throws SQLException { try (Connection c = DatabaseConnection.getInstance().getConnection(); PreparedStatement s = c.prepareStatement(sql); ResultSet r = s.executeQuery()) { r.next(); return r.getInt(1); } }
    private Libro map(ResultSet r) throws SQLException { return new Libro(r.getInt("id_libro"), r.getString("titulo"), r.getString("autor"), r.getString("isbn"), r.getInt("anio_publicacion"), r.getString("genero"), r.getBoolean("disponible")); }
}
