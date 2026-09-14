package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.UsuarioDAO;
import cl.m5proyecto.biblioteca.model.Usuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null || email.isBlank() || password.isBlank()) { response.sendRedirect(request.getContextPath() + "/index.jsp?error=1"); return; }
        try {
            Usuario usuario = usuarioDAO.validarLogin(email.trim(), password);
            if (usuario == null) { response.sendRedirect(request.getContextPath() + "/index.jsp?error=1"); return; }
            HttpSession session = request.getSession(true); session.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect(request.getContextPath() + "/app/dashboard");
        } catch (Exception exception) { throw new IOException("No fue posible validar el acceso", exception); }
    }
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { response.sendRedirect(request.getContextPath() + "/index.jsp"); }
}
