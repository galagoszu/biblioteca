# 📚 Biblioteca Digital UNTEC

Aplicación web desarrollada para el **Módulo 5 del Bootcamp Full Stack Java Trainee**, orientada a la gestión de una biblioteca digital mediante una arquitectura MVC.

El sistema permite administrar libros, usuarios, préstamos y devoluciones, incorporando autenticación, manejo de sesiones y control de acceso según el rol del usuario.

---

## 📋 Descripción

**Biblioteca Digital UNTEC** es una aplicación web desarrollada utilizando tecnologías del ecosistema Java para gestionar los principales procesos de una biblioteca.

La aplicación permite:

* Gestionar un catálogo de libros.
* Registrar y administrar usuarios.
* Realizar préstamos de libros.
* Registrar devoluciones.
* Controlar la disponibilidad de los libros.
* Gestionar permisos según el tipo de usuario.
* Mantener sesiones de usuario mediante `HttpSession`.

El proyecto implementa una arquitectura **MVC (Model - View - Controller)** junto con el patrón **DAO** para separar la lógica de negocio, presentación y acceso a datos.

---

## ✨ Funcionalidades

### 🔐 Autenticación y usuarios

* Inicio de sesión.
* Cierre de sesión.
* Manejo de sesiones mediante `HttpSession`.
* Control de acceso según el rol del usuario.
* Usuarios activos e inactivos.

### 📚 Gestión de libros

* Visualización del catálogo.
* Información de título, autor, ISBN, año y género.
* Control de disponibilidad.
* Gestión del catálogo para usuarios con rol `BIBLIOTECARIO`.

### 🔄 Préstamos y devoluciones

* Registro de préstamos.
* Registro de devoluciones.
* Asociación entre usuarios y libros.
* Control de disponibilidad de los libros.

### 🎨 Interfaz

* Diseño web responsive.
* Interfaz desarrollada con HTML5 y CSS3.
* Estética inspirada en un concepto **cyberpunk universitario**.

---

# 🛠️ Tecnologías utilizadas

| Tecnología          | Uso                                    |
| ------------------- | -------------------------------------- |
| **Java 17**         | Lenguaje principal                     |
| **Maven**           | Gestión de dependencias y construcción |
| **Jakarta Servlet** | Controladores y solicitudes HTTP       |
| **JSP**             | Vistas dinámicas                       |
| **JSTL**            | Etiquetas para las vistas JSP          |
| **JDBC**            | Conexión con la base de datos          |
| **DAO**             | Acceso y persistencia de datos         |
| **MariaDB**         | Base de datos relacional               |
| **Apache Tomcat**   | Servidor de aplicaciones               |
| **HTML5**           | Estructura de las páginas              |
| **CSS3**            | Diseño y estilos                       |

---

# 🚀 Instalación y configuración

Para ejecutar este proyecto en otro computador es necesario:

1. Clonar el repositorio.
2. Tener Java 17 instalado.
3. Tener Maven instalado.
4. Tener MariaDB instalado.
5. Crear y configurar la base de datos.
6. Configurar las credenciales de conexión.
7. Compilar el proyecto.
8. Ejecutarlo mediante Apache Tomcat.

---

## 1. Clonar el repositorio

Desde una terminal:

```bash
git clone https://github.com/galagoszu/biblioteca.git
```

Ingresar a la carpeta:

```bash
cd biblioteca
```

También es posible utilizar la opción **Clone Repository** de Visual Studio Code.

---

## 2. Crear la base de datos

Abrir MariaDB utilizando el cliente de preferencia.

Crear la base de datos:

```sql
CREATE DATABASE biblioteca_untec;
```

Seleccionar la base de datos:

```sql
USE biblioteca_untec;
```

---

## 3. Crear las tablas

Ejecutar las siguientes consultas SQL:

```sql
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'USUARIO',
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE libros (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    isbn VARCHAR(30) NOT NULL UNIQUE,
    anio_publicacion INT,
    genero VARCHAR(80),
    disponible BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NULL,

    CONSTRAINT fk_prestamo_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario),

    CONSTRAINT fk_prestamo_libro
        FOREIGN KEY (id_libro)
        REFERENCES libros(id_libro)
);
```

---

## 4. Crear los usuarios iniciales

Ejecutar:

```sql
INSERT INTO usuarios (nombre, email, password, rol, activo)
VALUES
(
    'Administrador Biblioteca',
    'admin@untec.cl',
    '123456',
    'BIBLIOTECARIO',
    TRUE
),
(
    'Juan Pérez',
    'juan@untec.cl',
    '123456',
    'USUARIO',
    TRUE
),
(
    'Maria González',
    'maria@untec.cl',
    '123456',
    'USUARIO',
    TRUE
);
```

---

## 5. Crear los libros iniciales

Ejecutar:

```sql
INSERT INTO libros (
    titulo,
    autor,
    isbn,
    anio_publicacion,
    genero,
    disponible
)
VALUES
(
    'Cien años de soledad',
    'Gabriel García Márquez',
    '9780307474728',
    1967,
    'Realismo mágico',
    TRUE
),
(
    '1984',
    'George Orwell',
    '9780451524935',
    1949,
    'Ciencia ficción',
    TRUE
),
(
    'El Principito',
    'Antoine de Saint-Exupéry',
    '9780156012195',
    1943,
    'Literatura',
    TRUE
),
(
    'Don Quijote de la Mancha',
    'Miguel de Cervantes',
    '9788420412146',
    1605,
    'Clásico',
    TRUE
),
(
    'Clean Code',
    'Robert C. Martin',
    '9780132350884',
    2008,
    'Programación',
    TRUE
),
(
    'Java: The Complete Reference',
    'Herbert Schildt',
    '9781260440232',
    2020,
    'Programación',
    TRUE
);
```

---

# ⚙️ Configuración de la conexión con MariaDB

El proyecto utiliza el archivo:

```text
src/main/resources/db.properties
```

Este archivo **no se encuentra incluido en el repositorio**, ya que contiene las credenciales locales de conexión a MariaDB.

Después de clonar el proyecto, crear manualmente:

```text
src/main/resources/db.properties
```

Agregar:

```properties
db.url=jdbc:mariadb://localhost:3307/biblioteca_untec
db.username=root
db.password=TU_CONTRASEÑA
```

Reemplazar:

```text
TU_CONTRASEÑA
```

por la contraseña correspondiente al usuario de MariaDB instalado localmente.

---

## 🔌 Puerto de MariaDB

El proyecto está configurado inicialmente para utilizar el puerto:

```text
3307
```

Si la instalación local de MariaDB utiliza otro puerto, por ejemplo `3306`, modificar la URL:

```properties
db.url=jdbc:mariadb://localhost:3306/biblioteca_untec
```

La base de datos debe llamarse:

```text
biblioteca_untec
```

---

# 📦 Compilación del proyecto

Desde la carpeta raíz del proyecto, donde se encuentra el archivo `pom.xml`, ejecutar:

```bash
mvn clean package
```

Si la compilación es correcta, Maven debería mostrar:

```text
BUILD SUCCESS
```

El proceso generará los archivos necesarios dentro de:

```text
target/
```

incluyendo el archivo:

```text
target/biblioteca.war
```

---

# 🐱 Configuración de Apache Tomcat

## 1. Agregar el proyecto a Tomcat

Desde Visual Studio Code, utilizando la extensión de Tomcat:

1. Agregar o seleccionar el servidor Apache Tomcat instalado localmente.
2. Iniciar el servidor.
3. Seleccionar **Add Deployment**.
4. Seleccionar **Exploded**.
5. Seleccionar la carpeta:

```text
target/biblioteca/
```

La carpeta seleccionada debe contener elementos similares a:

```text
META-INF/
WEB-INF/
index.jsp
```

6. Agregar el deployment.
7. Ejecutar **Publish Server (Full)** para publicar el proyecto.
8. Iniciar o reiniciar el servidor Tomcat.

---

# 🌐 Acceder a la aplicación

Con Apache Tomcat iniciado, abrir el navegador:

```text
http://localhost:8080/biblioteca/
```

---

# 👤 Usuarios para probar la aplicación

La base de datos incluye tres usuarios iniciales.

## 👨‍💼 Administrador Biblioteca

**Nombre:**

```text
Administrador Biblioteca
```

**Correo:**

```text
admin@untec.cl
```

**Contraseña:**

```text
123456
```

**Rol:**

```text
BIBLIOTECARIO
```

Este usuario permite probar las funcionalidades correspondientes al bibliotecario y la gestión del catálogo.

---

## 👤 Juan Pérez

**Nombre:**

```text
Juan Pérez
```

**Correo:**

```text
juan@untec.cl
```

**Contraseña:**

```text
123456
```

**Rol:**

```text
USUARIO
```

---

## 👤 Maria González

**Nombre:**

```text
Maria González
```

**Correo:**

```text
maria@untec.cl
```

**Contraseña:**

```text
123456
```

**Rol:**

```text
USUARIO
```

---

### 🔑 Contraseña inicial

Los tres usuarios utilizan la misma contraseña inicial:

```text
123456
```

> **Nota:** Estas credenciales corresponden únicamente a usuarios de prueba del proyecto académico.

---

# 🧪 Inicio de pruebas recomendado

Para comenzar a probar la aplicación se recomienda iniciar sesión primero con el usuario bibliotecario:

```text
Correo: admin@untec.cl
Contraseña: 123456
```

El usuario posee el rol:

```text
BIBLIOTECARIO
```

por lo que permite probar las funcionalidades de administración y gestión del catálogo.

Posteriormente, cerrar sesión y probar con un usuario normal:

```text
Correo: juan@untec.cl
Contraseña: 123456
```

o:

```text
Correo: maria@untec.cl
Contraseña: 123456
```

Estos usuarios poseen el rol:

```text
USUARIO
```

y permiten probar las funcionalidades correspondientes a un usuario normal, incluyendo el proceso de préstamos y devoluciones.

---

# 🏗️ Arquitectura del proyecto

El proyecto utiliza una arquitectura:

```text
MVC
(Model - View - Controller)
```

La estructura separa las principales responsabilidades de la aplicación.

### Model

Representa las entidades y datos utilizados por el sistema.

### View

Utiliza **JSP + JSTL** para presentar la información al usuario.

### Controller

Utiliza **Jakarta Servlets** para gestionar las solicitudes HTTP y coordinar las operaciones de la aplicación.

### DAO

Implementa el patrón **Data Access Object**, encargado de gestionar el acceso a la base de datos mediante JDBC.

### Session

Utiliza `HttpSession` para controlar las sesiones y mantener la información del usuario autenticado.

---

## 📂 Estructura conceptual

```text
biblioteca/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── ...
│       │
│       ├── resources/
│       │   └── db.properties
│       │
│       └── webapp/
│           ├── WEB-INF/
│           ├── index.jsp
│           └── ...
│
├── target/
│
├── pom.xml
└── README.md
```

> La estructura exacta de paquetes y archivos puede variar según la versión del proyecto.

---

# 🔐 Configuración de seguridad

El archivo:

```text
src/main/resources/db.properties
```

contiene las credenciales utilizadas para conectarse a MariaDB y **no debe subirse al repositorio público**.

Cada persona que utilice el proyecto debe crear su propio archivo:

```text
db.properties
```

utilizando las credenciales correspondientes a su instalación local.

Las credenciales de los usuarios de prueba indicadas en este README corresponden al inicio de sesión de la aplicación y son independientes de las credenciales utilizadas para conectarse a MariaDB.

### ⚠️ Recomendación

Para un entorno de producción, las contraseñas de los usuarios deberían almacenarse utilizando un mecanismo seguro de **hashing**, en lugar de almacenarlas directamente como texto plano.

---

# 🎓 Proyecto académico

**Bootcamp Full Stack Java Trainee**

**Módulo 5 – Aplicaciones Web Dinámicas con Java**

### Proyecto

**Biblioteca Digital UNTEC**

---

## 👨‍💻 Autor

**Gabriel Lagos**

Proyecto desarrollado con fines académicos como parte del proceso de formación **Full Stack Java**.
