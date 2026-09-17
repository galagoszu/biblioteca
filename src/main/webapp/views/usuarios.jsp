<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es"><head><title>Gestión de usuarios // UNTEC</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head><body>
<%@ include file="partials/nav.jspf" %>
<main class="page narrow">
  <header class="page-header compact">
    <p class="eyebrow">USER MANAGEMENT / 05</p>
    <h1>Gestión de usuarios</h1>
  </header>

  <c:if test="${param.ok eq 'updated'}">
    <div class="alert success">Estado actualizado correctamente.</div>
  </c:if>

  <div class="table-wrap">
    <table class="data-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Correo electrónico</th>
          <th>Estado</th>
          <th>Activar / Desactivar</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty usuarios}">
            <tr>
              <td colspan="4">No existen usuarios con rol USUARIO.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="usuario" items="${usuarios}">
              <tr>
                <td><c:out value="${usuario.nombre}" /></td>
                <td><c:out value="${usuario.email}" /></td>
                <td>
                  <span class="status-pill ${usuario.activo ? 'active' : 'inactive'}">
                    <c:choose>
                      <c:when test="${usuario.activo}">ACTIVO</c:when>
                      <c:otherwise>INACTIVO</c:otherwise>
                    </c:choose>
                  </span>
                </td>
                <td>
                  <form method="post" action="${pageContext.request.contextPath}/app/usuarios">
                    <input type="hidden" name="idUsuario" value="${usuario.idUsuario}" />
                    <input type="hidden" name="activo" value="${not usuario.activo}" />
                    <label class="switch" aria-label="Cambiar estado de ${usuario.nombre}">
                      <input type="checkbox" ${usuario.activo ? 'checked' : ''} onchange="this.form.submit()">
                      <span class="slider round"></span>
                    </label>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>
</main>
</body></html>
