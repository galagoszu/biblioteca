package cl.m5proyecto.biblioteca.controller;

import cl.m5proyecto.biblioteca.dao.PrestamoDAO;
import cl.m5proyecto.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/prestamos")
public class PrestamoServlet extends HttpServlet {
    private final PrestamoDAO dao = new PrestamoDAO();
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { try { Usuario u=(Usuario)request.getSession().getAttribute("usuarioLogueado"); if (u == null) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; } boolean librarian="BIBLIOTECARIO".equalsIgnoreCase(u.getRol()); request.setAttribute("prestamos", librarian ? dao.listarTodos() : dao.listarPorUsuario(u.getIdUsuario())); request.getRequestDispatcher("/views/prestamos.jsp").forward(request,response); } catch(Exception e) { throw new ServletException("No fue posible cargar préstamos",e); } }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException { try { Usuario u=(Usuario)request.getSession().getAttribute("usuarioLogueado"); if (u == null) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; } if (!u.isActivo()) { response.sendRedirect(request.getContextPath()+"/app/prestamos?error=inactive"); return; } boolean ok=dao.crearPrestamo(u.getIdUsuario(),Integer.parseInt(request.getParameter("idLibro"))); response.sendRedirect(request.getContextPath()+"/app/prestamos?"+(ok?"ok=loan":"error=unavailable")); } catch(Exception e){throw new IOException("No fue posible crear el préstamo",e);} }
}
