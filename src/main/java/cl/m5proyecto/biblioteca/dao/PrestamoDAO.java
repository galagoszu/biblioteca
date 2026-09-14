package cl.m5proyecto.biblioteca.dao;

import cl.m5proyecto.biblioteca.config.DatabaseConnection;
import cl.m5proyecto.biblioteca.model.Prestamo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private static final String QUERY = "SELECT p.id_prestamo, p.id_usuario, p.id_libro, u.nombre, l.titulo, p.fecha_prestamo, p.fecha_devolucion FROM prestamos p JOIN usuarios u ON u.id_usuario=p.id_usuario JOIN libros l ON l.id_libro=p.id_libro";

    public List<Prestamo> listarTodos() throws SQLException { return query(QUERY + " ORDER BY p.fecha_prestamo DESC"); }
    public List<Prestamo> listarPorUsuario(int idUsuario) throws SQLException { return query(QUERY + " WHERE p.id_usuario = ? ORDER BY p.fecha_prestamo DESC", idUsuario); }
    public int contarActivos() throws SQLException { return count("SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL"); }
    public int contarActivos(int idUsuario) throws SQLException { return count("SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? AND fecha_devolucion IS NULL", idUsuario); }

    public boolean crearPrestamo(int idUsuario, int idLibro) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement lock = connection.prepareStatement("UPDATE libros SET disponible = false WHERE id_libro = ? AND disponible = true");
                 PreparedStatement insert = connection.prepareStatement("INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo) VALUES (?, ?, ?)");) {
                lock.setInt(1, idLibro);
                if (lock.executeUpdate() != 1) { connection.rollback(); return false; }
                insert.setInt(1, idUsuario); insert.setInt(2, idLibro); insert.setDate(3, Date.valueOf(LocalDate.now())); insert.executeUpdate();
                connection.commit(); return true;
            } catch (SQLException exception) { connection.rollback(); throw exception; }
        }
    }

    public boolean registrarDevolucion(int idPrestamo) throws SQLException {
        return registrarDevolucion(idPrestamo, null);
    }

    public boolean registrarDevolucion(int idPrestamo, Integer idUsuario) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            String ownerClause = idUsuario == null ? "" : " AND id_usuario = ?";
            try (PreparedStatement updateLoan = connection.prepareStatement("UPDATE prestamos SET fecha_devolucion = ? WHERE id_prestamo = ? AND fecha_devolucion IS NULL" + ownerClause);
                 PreparedStatement updateBook = connection.prepareStatement("UPDATE libros SET disponible = true WHERE id_libro = (SELECT id_libro FROM prestamos WHERE id_prestamo = ?)");) {
                updateLoan.setDate(1, Date.valueOf(LocalDate.now())); updateLoan.setInt(2, idPrestamo);
                if (idUsuario != null) updateLoan.setInt(3, idUsuario);
                if (updateLoan.executeUpdate() != 1) { connection.rollback(); return false; }
                updateBook.setInt(1, idPrestamo); updateBook.executeUpdate(); connection.commit(); return true;
            } catch (SQLException exception) { connection.rollback(); throw exception; }
        }
    }

    private List<Prestamo> query(String sql, Integer... parameter) throws SQLException {
        List<Prestamo> loans = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            if (parameter.length > 0) statement.setInt(1, parameter[0]);
            try (ResultSet result = statement.executeQuery()) { while (result.next()) { Prestamo p = new Prestamo(); p.setIdPrestamo(result.getInt("id_prestamo")); p.setIdUsuario(result.getInt("id_usuario")); p.setIdLibro(result.getInt("id_libro")); p.setNombreUsuario(result.getString("nombre")); p.setTituloLibro(result.getString("titulo")); p.setFechaPrestamo(result.getDate("fecha_prestamo").toLocalDate()); if (result.getDate("fecha_devolucion") != null) p.setFechaDevolucion(result.getDate("fecha_devolucion").toLocalDate()); loans.add(p); } }
        }
        return loans;
    }
    private int count(String sql, Integer... parameter) throws SQLException { try (Connection c = DatabaseConnection.getInstance().getConnection(); PreparedStatement s = c.prepareStatement(sql)) { if (parameter.length > 0) s.setInt(1, parameter[0]); try (ResultSet r = s.executeQuery()) { r.next(); return r.getInt(1); } } }
}
