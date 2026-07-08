# BookGroup

Sistema de reservas desarrollado con Java y Spring Boot para practicar el manejo de LocalDateTime y la incorporación de un centralizador global de excepciones. La aplicación ofrece una API REST sencilla para gestionar empleados, salas y reservas usando H2 como base de datos embebida.

## Características

- CRUD para entidades: Empleado, Sala y Reserva.
- Uso de DTOs para las operaciones de entrada/salida en la API.
- Controlador global de excepciones (centralizador) que devuelve respuestas de error consistentes.
- Uso práctico de LocalDateTime para validaciones y comprobación de solapamiento de reservas.
- Configuración lista para ejecutar con Maven Wrapper, Docker y docker-compose.

## Stack

- Lenguaje: Java
- Framework / runtime: Spring Boot (Spring Web, Spring Data JPA)
- Persistencia: H2 (base de datos en memoria / embebida)
- Notable: DTOs, mapeadores, manejo global de excepciones

## Estructura principal

```
.gitattributes
.gitignore
.mvn/
Dockerfile
docker-compose.yml
mvnw
mvnw.cmd
pom.xml
src/
  main/
    java/
      com/BookGroup/BookGroup/
        BookGroupApplication.java         # clase main de la aplicación
        controller/                       # controllers REST (Empleado, Sala, Reserva)
        dto/                              # objetos de transferencia (EmpleadoDTO, SalaDTO, ReservaDTO)
        entity/                           # entidades JPA (Empleado, Sala, Reserva, Estado)
        excepcion/                        # manejo global de excepciones (GlobalExceptionHandler, ErrorResponse)
        mapper/                           # mapeadores DTO <-> entidad
        repository/                       # interfaces Spring Data JPA
        service/                          # lógica de negocio y validaciones
```

Cómo encajan: la aplicación arranca desde `BookGroupApplication`; las peticiones HTTP son atendidas por los controllers que usan DTOs, servicios y repositorios para acceso a la persistencia (H2). El paquete `excepcion` centraliza el manejo de errores para devolver respuestas uniformes.

## Cómo ejecutar (rápido)

1) Clona el repositorio:

```bash
git clone https://github.com/victordaniel123rt-lang/BookGroup.git
cd BookGroup
```

2) Usando el wrapper Maven (recomendado):

- Ejecutar en desarrollo:

```bash
./mvnw spring-boot:run
```

- Compilar y ejecutar el JAR:

```bash
./mvnw clean package
java -jar target/*.jar
```

3) Usando Docker:

- Construir la imagen:

```bash
docker build -t bookgroup .
```

- Ejecutar el contenedor:

```bash
docker run -p 8080:8080 bookgroup
```

4) Usando docker-compose:

```bash
docker-compose up --build
```

Por defecto la aplicación arranca en el puerto 8080 (si no se sobrescribe en propiedades). La consola de H2 suele estar disponible en `/h2-console` cuando está habilitada por la configuración de Spring Boot; la URL JDBC por defecto en proyectos Spring Boot suele ser `jdbc:h2:mem:testdb`.

## Endpoints (orientativos)

El proyecto contiene controladores para:

- Empleado — operaciones CRUD para empleados (`EmpleadoController`)
- Sala — operaciones CRUD para salas (`SalaController`)
- Reserva — crear/consultar/modificar/eliminar reservas (`ReservaController`)

Nota: las rutas exactas y prefijos (por ejemplo `/empleados` o `/api/empleados`) dependen de las anotaciones en cada controller; revisa las clases en `src/main/java/com/BookGroup/BookGroup/controller` para conocer las rutas y los formatos de request/response.

Ejemplo genérico con curl (ajusta ruta/puerto según el código):

```bash
# obtener lista de reservas
curl http://localhost:8080/reservas

# crear reserva (ejemplo JSON)
curl -X POST http://localhost:8080/reservas \
  -H "Content-Type: application/json" \
  -d '{"salaId":1,"empleadoId":1,"inicio":"2026-07-08T09:00:00","fin":"2026-07-08T10:00:00"}'
```

## Puntos importantes en el código

- DTOs en `src/main/java/.../dto`: encapsulan los datos expuestos por la API.
- Entities en `src/main/java/.../entity`: modelos JPA para persistencia.
- `GlobalExceptionHandler` en `src/main/java/.../excepcion`: centraliza respuestas de error (`ErrorResponse`).
- Repositorios en `src/main/java/.../repository`: interfaces Spring Data JPA para acceso a datos.
- Servicios en `src/main/java/.../service`: lógica de negocio y validaciones (p. ej. conflicto de reservas, overlap de `LocalDateTime`).

## Desarrollo y pruebas

- Compilación: `./mvnw clean package`
- Ejecutar en modo development: `./mvnw spring-boot:run`
- Tests (si hay tests implementados): `./mvnw test`

## Recomendaciones

- Revisar las validaciones relacionadas con `LocalDateTime` en los servicios para evitar solapamiento de reservas.
- Si necesitas persistencia durable, cambia la configuración de H2 en `application.properties`/`application.yml` para usar un archivo en disco o sustituir por otra base de datos (Postgres, MySQL).
- Consulta los controllers para ver los contratos exactos (campos requeridos en los DTOs) antes de integrar clientes.

## Contribuir

- Abrir issues para errores o mejoras.
- Hacer PRs contra la rama principal con una descripción clara del cambio.
- Mantener estilo y pruebas cuando agregues lógica de negocio crítica (validaciones de reservas).

## Licencia

Añade aquí la licencia del proyecto (por ejemplo: MIT) si procede.

---

Si quieres, puedo:
- Extraer y listar las rutas exactas y ejemplos concretos leyendo las anotaciones en cada controller y añadir ejemplos curl precisos.
- Añadir un archivo de configuración de ejemplo para `application.properties` que documente la conexión H2.
