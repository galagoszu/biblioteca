# 📚 Biblioteca Digital UNTEC

Aplicación web desarrollada para el **Módulo 5 del Bootcamp Full Stack Java Trainee**, orientada a la gestión de una biblioteca digital mediante una arquitectura **MVC (Model - View - Controller)**.

El sistema permite administrar libros, usuarios, préstamos y devoluciones, incorporando autenticación, manejo de sesiones, control de acceso según el rol del usuario y gestión del estado activo/inactivo de los usuarios.

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
* Activar y desactivar usuarios.
* Mantener sesiones de usuario mediante `HttpSession`.

El proyecto implementa una arquitectura **MVC** junto con el patrón **DAO (Data Access Object)** para separar la lógica de negocio, presentación y acceso a datos.

---

# ✨ Funcionalidades

## 🔐 Autenticación y usuarios

* Inicio de sesión.
* Cierre de sesión.
* Manejo de sesiones mediante `HttpSession`.
* Control de acceso según el rol del usuario.
* Gestión de usuarios por parte del bibliotecario.
* Activación y desactivación de usuarios.
* Control del estado del usuario mediante el campo `activo`.
* Restricción de nuevos préstamos para usuarios inactivos.
* Permiso para realizar devoluciones aun cuando el usuario esté inactivo.

---

## 👥 Gestión de usuarios

El sistema permite al usuario con rol `BIBLIOTECARIO` administrar el estado de los usuarios registrados.

La gestión se realiza desde una vista específica de usuarios y permite:

* Visualizar usuarios cuyo rol sea `USUARIO`.
* Consultar nombre.
* Consultar correo electrónico.
* Consultar el estado actual.
* Activar usuarios.
* Desactivar usuarios.
* Guardar inmediatamente el cambio de estado en la base de datos.

El bibliotecario no aparece dentro de la lista de usuarios administrables.

### 🔄 Activación y desactivación

El estado del usuario se controla mediante el campo:

```text
activo
```

Los valores utilizados son:

```text
TRUE  → Usuario activo
FALSE → Usuario inactivo
```

La activación o desactivación no elimina al usuario ni modifica sus datos personales, contraseña o rol.

El cambio se almacena directamente en la base de datos mediante una actualización del registro correspondiente.

---

## 🟢 Usuario activo

Cuando un usuario se encuentra activo puede:

* Iniciar sesión.
* Acceder al dashboard.
* Consultar el catálogo.
* Navegar por la aplicación.
* Consultar información de los libros.
* Solicitar nuevos préstamos.
* Devolver libros que tenga actualmente prestados.

El comportamiento del usuario activo mantiene la funcionalidad normal de la aplicación.

---

## 🔴 Usuario inactivo

Un usuario inactivo no queda completamente bloqueado de la aplicación.

Puede:

* Iniciar sesión.
* Acceder al dashboard.
* Consultar el catálogo.
* Navegar por las páginas permitidas.
* Consultar información de los libros.
* Devolver libros que tenga actualmente prestados.

Sin embargo:

* No puede solicitar nuevos préstamos.
* El botón para solicitar un préstamo aparece deshabilitado.
* El backend también valida el estado del usuario antes de crear un nuevo préstamo.

La restricción no se aplica a las devoluciones.

Por lo tanto:

```text
USUARIO ACTIVO
├── Puede navegar
├── Puede consultar catálogo
├── Puede solicitar préstamos
└── Puede devolver préstamos

USUARIO INACTIVO
├── Puede navegar
├── Puede consultar catálogo
├── NO puede solicitar nuevos préstamos
└── Puede devolver préstamos existentes
```

---

## 📊 Estado de sesión

El dashboard muestra dinámicamente el estado del usuario autenticado mediante la sección:

```text
SESSION STATUS
```

Para un usuario activo:

```text
USUARIO · ACTIVO
```

Para un usuario inactivo:

```text
USUARIO · INACTIVO
```

Cuando el usuario se encuentra inactivo, además se muestra el mensaje:

> Tu cuenta está desactivada. Para activar tu usuario, acércate al bibliotecario.

El estado se obtiene dinámicamente desde la información del usuario autenticado y no se encuentra escrito de forma fija en la vista.

---

## 📚 Gestión de libros

* Visualización del catálogo.
* Información de título, autor, ISBN, año y género.
* Control de disponibilidad.
* Gestión del catálogo para usuarios con rol `BIBLIOTECARIO`.

---

## 🔄 Préstamos y devoluciones

* Registro de préstamos.
* Registro de devoluciones.
* Asociación entre usuarios y libros.
* Control de disponibilidad de los libros.
* Validación del estado del usuario antes de registrar nuevos préstamos.
* Permiso de devolución para usuarios inactivos.

La validación del estado activo se aplica solamente al momento de crear un nuevo préstamo.

---

# 🎨 Interfaz

La aplicación cuenta con:

* Diseño web responsive.
* Interfaz desarrollada con HTML5 y CSS3.
* Estética inspirada en un concepto cyberpunk universitario.
* Fondo oscuro.
* Detalles en verde neón.
* Diseño consistente entre las diferentes vistas de la aplicación.

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
| **HttpSession**     | Gestión de sesiones                    |

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

# 1️⃣ Clonar el repositorio

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

# 2️⃣ Crear la base de datos

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

# 3️⃣ Crear las tablas

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

El campo:

```sql
activo BOOLEAN NOT NULL DEFAULT TRUE
```

permite controlar el estado de los usuarios.

---

# 4️⃣ Crear los usuarios iniciales

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

# 5️⃣ Crear los libros iniciales

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

# 🔌 Puerto de MariaDB

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

Este usuario permite probar las funcionalidades correspondientes al bibliotecario, la gestión del catálogo y la gestión de usuarios.

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

# 🔑 Contraseña inicial

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

por lo que permite probar las funcionalidades de:

* Administración.
* Gestión del catálogo.
* Gestión de usuarios.
* Activación y desactivación de usuarios.

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

# 🧪 Prueba de activación y desactivación de usuarios

Para probar la funcionalidad de gestión de usuarios:

### 1. Iniciar sesión como bibliotecario

Utilizar:

```text
admin@untec.cl
```

con contraseña:

```text
123456
```

---

### 2. Ingresar a la gestión de usuarios

El bibliotecario puede visualizar los usuarios que poseen el rol:

```text
USUARIO
```

---

### 3. Desactivar un usuario

Utilizar el control de activación/desactivación disponible en la vista de usuarios.

El cambio se guarda en la base de datos.

---

### 4. Iniciar sesión con el usuario desactivado

El usuario podrá iniciar sesión normalmente.

---

### 5. Revisar el dashboard

El estado debe aparecer como:

```text
USUARIO · INACTIVO
```

Además, debe mostrarse:

> Tu cuenta está desactivada. Para activar tu usuario, acércate al bibliotecario.

---

### 6. Revisar el catálogo

El usuario inactivo debe poder:

* Acceder al catálogo.
* Navegar por la aplicación.
* Consultar información de los libros.

---

### 7. Intentar solicitar un préstamo

El botón para solicitar un nuevo préstamo debe encontrarse deshabilitado.

Además, el backend debe impedir que se registre un nuevo préstamo para un usuario inactivo.

---

### 8. Realizar una devolución

Si el usuario ya posee un préstamo activo, debe poder devolver el libro aunque su cuenta se encuentre inactiva.

---

### 9. Reactivar el usuario

Volver a ingresar como bibliotecario y activar nuevamente al usuario.

---

### 10. Comprobar el estado activo

El usuario debería volver a mostrar:

```text
USUARIO · ACTIVO
```

y recuperar la posibilidad de solicitar nuevos préstamos.

---

# 🏗️ Arquitectura del proyecto

El proyecto utiliza una arquitectura:

```text
MVC
(Model - View - Controller)
```

La estructura separa las principales responsabilidades de la aplicación.

## Model

Representa las entidades y datos utilizados por el sistema.

## View

Utiliza **JSP + JSTL** para presentar la información al usuario.

## Controller

Utiliza **Jakarta Servlets** para gestionar las solicitudes HTTP y coordinar las operaciones de la aplicación.

## DAO

Implementa el patrón **Data Access Object**, encargado de gestionar el acceso a la base de datos mediante JDBC.

El `UsuarioDAO` permite trabajar con la información de los usuarios y actualizar su estado de activación/desactivación.

## Session

Utiliza `HttpSession` para controlar las sesiones y mantener la información del usuario autenticado.

La información utilizada por la sesión permite identificar, entre otros datos, el usuario, su rol y su estado de activación.

---

# 📂 Estructura conceptual

```text
biblioteca/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── cl/
│       │       └── m5proyecto/
│       │           └── biblioteca/
│       │               ├── controller/
│       │               ├── dao/
│       │               ├── model/
│       │               └── ...
│       │
│       ├── resources/
│       │   └── db.properties
│       │
│       └── webapp/
│           ├── WEB-INF/
│           ├── views/
│           │   ├── home.jsp
│           │   ├── libros.jsp
│           │   ├── usuarios.jsp
│           │   └── ...
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

# 🗄️ Modelo de datos

La aplicación utiliza tres entidades principales:

```text
USUARIOS
   │
   │ 1:N
   ▼
PRESTAMOS
   │
   │ N:1
   ▼
LIBROS
```

### Usuarios

Contiene la información de las personas registradas en el sistema, incluyendo:

* Identificador.
* Nombre.
* Correo electrónico.
* Contraseña.
* Rol.
* Estado activo/inactivo.

### Libros

Contiene la información del catálogo:

* Identificador.
* Título.
* Autor.
* ISBN.
* Año de publicación.
* Género.
* Disponibilidad.

### Préstamos

Relaciona usuarios y libros y permite registrar:

* Usuario que realiza el préstamo.
* Libro prestado.
* Fecha del préstamo.
* Fecha de devolución.

---

# 🔄 Flujo general de funcionamiento

```text
                    ┌─────────────────┐
                    │      LOGIN      │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   HttpSession   │
                    └────────┬────────┘
                             │
              ┌──────────────┴──────────────┐
              │                             │
              ▼                             ▼
      ┌───────────────┐             ┌───────────────┐
      │ BIBLIOTECARIO │             │    USUARIO    │
      └───────┬───────┘             └───────┬───────┘
              │                             │
              ▼                             ▼
     Gestión de usuarios             Catálogo / préstamos
              │                             │
              ▼                             ▼
     Activar / desactivar          Validar estado activo
                                            │
                              ┌─────────────┴─────────────┐
                              │                           │
                              ▼                           ▼
                       Usuario activo              Usuario inactivo
                              │                           │
                              ▼                           ▼
                       Nuevo préstamo             Sin nuevo préstamo
                              │                           │
                              └─────────────┬─────────────┘
                                            ▼
                                       Devolución
```

---

# 🧩 Reglas de negocio principales

El sistema considera las siguientes reglas:

1. Solo los usuarios con rol `BIBLIOTECARIO` pueden gestionar usuarios.
2. Los usuarios administrables corresponden al rol `USUARIO`.
3. El bibliotecario no aparece dentro de la lista de usuarios administrables.
4. Un usuario inactivo puede iniciar sesión.
5. Un usuario inactivo puede navegar por las secciones permitidas.
6. Un usuario inactivo puede consultar el catálogo.
7. Un usuario inactivo no puede solicitar nuevos préstamos.
8. La interfaz deshabilita la opción de solicitar préstamos para usuarios inactivos.
9. El backend también valida el estado antes de registrar un préstamo.
10. Un usuario inactivo puede devolver préstamos existentes.
11. Reactivar un usuario permite recuperar la posibilidad de solicitar nuevos préstamos.
12. La activación o desactivación no elimina ni modifica los demás datos del usuario.

---

# 🧰 Requisitos previos

Antes de ejecutar el proyecto se recomienda contar con:

* **Java JDK 17**
* **Apache Maven**
* **MariaDB**
* **Apache Tomcat**
* **Visual Studio Code** u otro IDE compatible
* Extensión de Tomcat para Visual Studio Code, si se desea realizar el deployment desde el IDE.

---

# 📌 Consideraciones

* El puerto predeterminado de Tomcat utilizado en este README es `8080`.
* El puerto de MariaDB configurado inicialmente es `3307`.
* La base de datos debe llamarse `biblioteca_untec`.
* El archivo `db.properties` debe configurarse de forma local.
* Las credenciales incluidas son únicamente para pruebas académicas.
* El proyecto no debe utilizar estas credenciales en un entorno productivo.
* Las contraseñas de producción deben almacenarse mediante mecanismos seguros de hashing.
* El archivo `db.properties` debe mantenerse fuera del repositorio público.

---

# 🎓 Proyecto académico

**Bootcamp Full Stack Java Trainee**

**Módulo 5 – Aplicaciones Web Dinámicas con Java**

### Proyecto

**Biblioteca Digital UNTEC**

---

# 👨‍💻 Autor

**Gabriel Lagos**

Proyecto desarrollado con fines académicos como parte del proceso de formación **Full Stack Java**.

