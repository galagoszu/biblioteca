package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.LibroDAO;
import cl.m5proyecto.biblioteca.model.Libro;
import cl.m5proyecto.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/libros")
public class LibroServlet extends HttpServlet {
    private final LibroDAO dao = new LibroDAO();
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { String action = request.getParameter("action"); if ("nuevo".equals(action) || "editar".equals(action)) { if (!isLibrarian(request)) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; } if ("editar".equals(action)) request.setAttribute("libro", dao.buscarPorId(Integer.parseInt(request.getParameter("id")))); request.getRequestDispatcher("/views/libro-form.jsp").forward(request, response); return; } request.setAttribute("libros", dao.listarTodos(request.getParameter("q"))); request.getRequestDispatcher("/views/libros.jsp").forward(request, response); }
        catch (Exception exception) { throw new ServletException("No fue posible cargar libros", exception); }
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { try { if (!isLibrarian(request)) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; } String action = request.getParameter("action"); if ("eliminar".equals(action)) { if (!dao.eliminar(Integer.parseInt(request.getParameter("id")))) response.sendRedirect(request.getContextPath()+"/app/libros?error=active"); else response.sendRedirect(request.getContextPath()+"/app/libros?ok=deleted"); return; } Libro l = libro(request); if ("actualizar".equals(action)) { l.setIdLibro(Integer.parseInt(request.getParameter("id"))); dao.actualizar(l); } else dao.crear(l); response.sendRedirect(request.getContextPath()+"/app/libros?ok=saved"); } catch (Exception exception) { throw new IOException("No fue posible guardar el libro", exception); } }
    private boolean isLibrarian(HttpServletRequest request) { Usuario user = (Usuario) request.getSession().getAttribute("usuarioLogueado"); return user != null && "BIBLIOTECARIO".equalsIgnoreCase(user.getRol()); }
    private Libro libro(HttpServletRequest r) { Libro l = new Libro(); l.setTitulo(r.getParameter("titulo")); l.setAutor(r.getParameter("autor")); l.setIsbn(r.getParameter("isbn")); l.setAnioPublicacion(Integer.parseInt(r.getParameter("anio_publicacion"))); l.setGenero(r.getParameter("genero")); l.setDisponible(true); return l; }
}
