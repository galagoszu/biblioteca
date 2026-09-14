package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.PrestamoDAO;
import cl.m5proyecto.biblioteca.model.Usuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/devolucion")
public class DevolucionServlet extends HttpServlet {
    private final PrestamoDAO dao = new PrestamoDAO();
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException { try { Usuario user=(Usuario)request.getSession().getAttribute("usuarioLogueado"); if (user == null) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; } Integer owner = "BIBLIOTECARIO".equalsIgnoreCase(user.getRol()) ? null : user.getIdUsuario(); boolean ok=dao.registrarDevolucion(Integer.parseInt(request.getParameter("idPrestamo")), owner); response.sendRedirect(request.getContextPath()+"/app/prestamos?"+(ok?"ok=returned":"error=already-returned")); } catch(Exception e){throw new IOException("No fue posible registrar la devolución",e);} }
}
