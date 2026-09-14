<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>UNTEC // Digital Library</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-page">
	<main class="login-shell">
		<section class="login-intro">
			<p class="eyebrow">UNTEC // DIGITAL LIBRARY</p>
			<h1>Conocimiento<br><span>en movimiento.</span></h1>
			<p class="intro-copy">Catálogo, préstamos y cultura universitaria en una sola interfaz.</p>
		</section>
		<section class="panel login-panel">
			<p class="panel-kicker">ACCESS GATE / 01</p>
			<h2>Iniciar sesión</h2>
			<c:if test="${not empty param.error}"><div class="alert error">Credenciales incorrectas. Intenta nuevamente.</div></c:if>
			<c:if test="${not empty param.logout}"><div class="alert success">Sesión cerrada correctamente.</div></c:if>
			<form action="${pageContext.request.contextPath}/login" method="post" class="form-stack">
				<label for="email">Correo electrónico</label>
				<input id="email" name="email" type="email" required autocomplete="email">
				<label for="password">Contraseña</label>
				<input id="password" name="password" type="password" required autocomplete="current-password">
				<button type="submit" class="button button-primary">ENTRAR <span>→</span></button>
			</form>
		</section>
	</main>
</body>
</html>
