package cl.m5proyecto.biblioteca.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/app/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getSession(false) == null || httpRequest.getSession(false).getAttribute("usuarioLogueado") == null) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
