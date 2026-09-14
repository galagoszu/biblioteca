package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.LibroDAO;
import cl.m5proyecto.biblioteca.dao.PrestamoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {
    private final LibroDAO libroDAO = new LibroDAO(); private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { request.setAttribute("totalLibros", libroDAO.contar()); request.setAttribute("librosDisponibles", libroDAO.contarDisponibles()); request.setAttribute("prestamosActivos", prestamoDAO.contarActivos()); request.getRequestDispatcher("/views/home.jsp").forward(request, response); }
        catch (Exception exception) { throw new ServletException("No fue posible cargar el dashboard", exception); }
    }
}
