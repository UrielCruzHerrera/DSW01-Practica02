# Quickstart: CRUD de Empleados

## Prerrequisitos
- Java 17
- Docker + Docker Compose
- Maven o Gradle según configuración del proyecto

## 1) Configurar variables de entorno (opcional)
Valores por defecto esperados en `application.properties`:
- `SPRING_SECURITY_USER_NAME=admin`
- `SPRING_SECURITY_USER_PASSWORD=admin123`
- `SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dsw01_practica02`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=postgres`

## 2) Levantar PostgreSQL con Docker
Ejemplo de comando:
- `docker compose -f docker-compose.local.yml up -d postgres`

## 3) Ejecutar aplicación Spring Boot
Ejemplo (Maven):
- `./mvnw spring-boot:run`

## 4) Verificar salud y documentación
- Health: `GET /actuator/health`
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`

## 5) Probar autenticación básica
Usar credenciales:
- usuario: `admin`
- contraseña: `admin123`

## 6) Flujos funcionales mínimos
- Crear empleado con `nombre`, `direccion`, `telefono` (la API genera `clave`).
- Consultar lista completa de empleados sin parámetros de paginación.
- Consultar lista paginada de empleados con tamaño de página 5 y metadatos (`content`, `page`, `size`, `totalElements`, `totalPages`).
- Consultar empleado por `clave`.
- Actualizar empleado existente.
- Eliminar empleado existente.
- Verificar `404 Not Found` al actualizar/eliminar una `clave` inexistente.

## 7) Validaciones esperadas
- `clave` generada por API con formato `E-` + número secuencial y máximo 50 caracteres.
- `nombre`, `direccion`, `telefono` obligatorios y máximo 100 caracteres cada uno.
- Secuencia de clave monotónica global sin reutilización tras eliminaciones.

## 8) Estado de verificación de implementación (2026-03-03)
- Compilación Maven (`mvn -DskipTests compile`): **OK**.
- Migraciones Flyway creadas: `V1__create_empleados_table.sql` y `V2__create_empleado_sequence.sql`.
- Endpoints CRUD implementados con Basic Auth y contrato OpenAPI.
- Validación E2E ejecutada con PostgreSQL en Docker (`POSTGRES_HOST_PORT=55432`) y API en `localhost:8080` con Basic Auth:
	- `GET /actuator/health` -> `200`
	- `GET /v3/api-docs` -> `200`
	- `POST /api/empleados` -> `201`
	- `GET /api/empleados/{clave}` -> `200`
	- `GET /api/empleados` -> `200`
	- `GET /api/empleados?page=0&size=5` -> `200`
	- `PUT /api/empleados/{clave}` -> `200`
	- `DELETE /api/empleados/{clave}` -> `204`
	- `GET /api/empleados/{clave}` (tras eliminar) -> `404`
- Verificación de rendimiento ligera sobre `GET /api/empleados?page=0&size=5` (60 muestras):
	- `min=84.13ms`, `avg=88.03ms`, `p95=92.98ms`, `max=122.76ms`
	- Resultado: **cumple** objetivo `p95 < 2s`.
