package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.UsuarioDAO;
import cl.m5proyecto.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/usuarios")
public class UsuarioServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"BIBLIOTECARIO".equalsIgnoreCase(usuarioLogueado.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            request.setAttribute("usuarios", usuarioDAO.obtenerUsuarios());
            request.getRequestDispatcher("/views/usuarios.jsp").forward(request, response);
        } catch (Exception exception) {
            throw new ServletException("No fue posible cargar la gestión de usuarios", exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"BIBLIOTECARIO".equalsIgnoreCase(usuarioLogueado.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            boolean activo = Boolean.parseBoolean(request.getParameter("activo"));
            usuarioDAO.actualizarEstadoUsuario(idUsuario, activo);
            response.sendRedirect(request.getContextPath() + "/app/usuarios?ok=updated");
        } catch (Exception exception) {
            throw new IOException("No fue posible actualizar el estado del usuario", exception);
        }
    }
}
