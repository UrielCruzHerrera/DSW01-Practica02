# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/001-crud-empleados/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: No se agregan tareas de test-first explícitas porque la especificación no exige TDD; se incluyen tareas de validación funcional y de rendimiento ligero al cierre.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicialización del proyecto backend y configuración base

- [x] T001 Crear estructura base de paquetes backend en src/main/java/com/company/empleados/
- [x] T002 Configurar dependencias Spring Boot 3, Security, Data JPA, Validation, Actuator, OpenAPI y PostgreSQL en pom.xml
- [x] T003 [P] Configurar propiedades base de aplicación y datasource en src/main/resources/application.properties
- [x] T004 [P] Configurar credenciales y seguridad HTTP Basic por variables de entorno en src/main/resources/application.properties
- [x] T005 [P] Crear configuración Docker local para aplicación y PostgreSQL en docker-compose.local.yml

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura transversal que bloquea la implementación de historias

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T006 Crear clase principal Spring Boot en src/main/java/com/company/empleados/EmpleadosApplication.java
- [x] T007 Implementar configuración global de seguridad HTTP Basic en src/main/java/com/company/empleados/config/SecurityConfig.java
- [x] T008 [P] Configurar OpenAPI con esquema Basic Auth en src/main/java/com/company/empleados/config/OpenApiConfig.java
- [x] T009 [P] Implementar excepción de recurso no encontrado en src/main/java/com/company/empleados/config/ResourceNotFoundException.java
- [x] T010 [P] Implementar manejador global de errores en src/main/java/com/company/empleados/config/GlobalExceptionHandler.java
- [x] T011 Crear migración inicial de tabla empleados en src/main/resources/db/migration/V1__create_empleados_table.sql
- [x] T012 Crear secuencia de base de datos para claves de empleados en src/main/resources/db/migration/V2__create_empleado_sequence.sql
- [x] T013 [P] Implementar validador del patrón de clave `E-<n>` para operaciones por path en src/main/java/com/company/empleados/validation/ClaveFormatValidator.java

**Checkpoint**: Foundation ready - user story implementation can now begin

---

## Phase 3: User Story 1 - Registrar empleado (Priority: P1) 🎯 MVP

**Goal**: Registrar empleados validando campos de negocio y generando clave automática monotónica

**Independent Test**: Al crear empleados válidos, la API genera `clave` `E-<n>` sin reutilizar eliminadas; datos inválidos son rechazados.

### Implementation for User Story 1

- [x] T014 [P] [US1] Crear entidad Empleado con restricciones de columnas en src/main/java/com/company/empleados/entity/Empleado.java
- [x] T015 [P] [US1] Crear repositorio de empleados en src/main/java/com/company/empleados/repository/EmpleadoRepository.java
- [x] T016 [P] [US1] Crear DTO de creación (sin campo `clave`) en src/main/java/com/company/empleados/dto/EmpleadoCreateRequest.java
- [x] T017 [P] [US1] Crear DTO de respuesta con `clave` generada en src/main/java/com/company/empleados/dto/EmpleadoResponse.java
- [x] T018 [US1] Implementar generador de clave `E-<n>` basado en secuencia DB en src/main/java/com/company/empleados/service/EmpleadoClaveGenerator.java
- [x] T019 [US1] Implementar servicio de creación con generación automática de clave y validaciones en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T020 [US1] Implementar endpoint POST /api/empleados retornando recurso creado en src/main/java/com/company/empleados/controller/EmpleadoController.java
- [x] T021 [US1] Mapear errores de validación y colisión de clave en src/main/java/com/company/empleados/config/GlobalExceptionHandler.java

**Checkpoint**: User Story 1 funcional y demostrable de forma independiente

---

## Phase 4: User Story 2 - Consultar empleados (Priority: P2)

**Goal**: Consultar por clave y listar empleados con paginación opcional y metadatos

**Independent Test**: Sin parámetros de paginación retorna arreglo completo; con paginación retorna objeto con `content`, `page`, `size`, `totalElements`, `totalPages` y tamaño 5.

### Implementation for User Story 2

- [x] T022 [P] [US2] Crear DTO de respuesta paginada en src/main/java/com/company/empleados/dto/EmpleadoPageResponse.java
- [x] T023 [US2] Implementar consulta por clave con 404 en servicio en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T024 [US2] Implementar listado sin paginación en servicio en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T025 [US2] Implementar listado paginado con metadatos y tamaño fijo 5 en servicio en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T026 [US2] Implementar endpoint GET /api/empleados/{clave} en src/main/java/com/company/empleados/controller/EmpleadoController.java
- [x] T027 [US2] Implementar endpoint GET /api/empleados con page/size opcionales en src/main/java/com/company/empleados/controller/EmpleadoController.java
- [x] T028 [US2] Rechazar parámetros de paginación inválidos en src/main/java/com/company/empleados/config/GlobalExceptionHandler.java

**Checkpoint**: User Stories 1 y 2 operativas y verificables por separado

---

## Phase 5: User Story 3 - Actualizar y eliminar empleado (Priority: P3)

**Goal**: Mantener catálogo con actualización/eliminación y manejo de inexistentes

**Independent Test**: Actualizar/eliminar existente funciona; sobre clave inexistente responde 404.

### Implementation for User Story 3

- [x] T029 [P] [US3] Crear DTO de actualización de empleado en src/main/java/com/company/empleados/dto/EmpleadoUpdateRequest.java
- [x] T030 [US3] Implementar lógica de actualización con validaciones en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T031 [US3] Implementar lógica de eliminación con validación de existencia en src/main/java/com/company/empleados/service/EmpleadoService.java
- [x] T032 [US3] Implementar endpoint PUT /api/empleados/{clave} en src/main/java/com/company/empleados/controller/EmpleadoController.java
- [x] T033 [US3] Implementar endpoint DELETE /api/empleados/{clave} retornando 204 en src/main/java/com/company/empleados/controller/EmpleadoController.java
- [x] T034 [US3] Garantizar respuesta 404 consistente para update/delete de inexistentes en src/main/java/com/company/empleados/config/GlobalExceptionHandler.java

**Checkpoint**: Todas las historias funcionales y validables independientemente

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de consistencia transversal, contrato y validación operativa

- [x] T035 [P] Alinear contrato final de API con implementación en specs/001-crud-empleados/contracts/empleados-api.yaml
- [x] T036 [P] Actualizar guía de ejecución/validación manual en specs/001-crud-empleados/quickstart.md
- [x] T037 Configurar logging estructurado para operaciones CRUD en src/main/resources/application.properties
- [x] T038 Verificar exposición de endpoints health/info y hardening básico en src/main/resources/application.properties
- [x] T039 Ejecutar validación manual end-to-end (Docker + Swagger + Auth + CRUD) y registrar resultados en specs/001-crud-empleados/quickstart.md
- [x] T040 Ejecutar verificación ligera de rendimiento para validar p95 < 2s en escenario de desarrollo y documentar resultados en specs/001-crud-empleados/quickstart.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: sin dependencias.
- **Foundational (Phase 2)**: depende de Setup y bloquea historias.
- **US1 (Phase 3)**: depende de Foundational.
- **US2 (Phase 4)**: depende de Foundational y reutiliza componentes base de US1.
- **US3 (Phase 5)**: depende de Foundational y reutiliza componentes base de US1.
- **Polish (Phase 6)**: depende de completar las historias seleccionadas.

### User Story Dependencies

- **US1 (P1)**: entrega MVP y habilita entidades/servicios base.
- **US2 (P2)**: independiente en valor de negocio tras foundation, reutiliza modelo/servicio base.
- **US3 (P3)**: independiente en valor de negocio tras foundation, reutiliza modelo/servicio base.

### Within Each User Story

- DTOs/entidades antes de servicios.
- Servicios antes de controladores.
- Manejo de errores y validación al cierre de cada historia.

### Parallel Opportunities

- Setup en paralelo: T003, T004, T005.
- Foundational en paralelo: T008, T009, T010, T013.
- US1 en paralelo: T014, T015, T016, T017.
- US2 en paralelo: T022 y T023.
- US3 en paralelo: T029 con preparación de servicio.
- Polish en paralelo: T035 y T036.

---

## Parallel Example: User Story 1

- Task: T014 [US1] Crear entidad Empleado en src/main/java/com/company/empleados/entity/Empleado.java
- Task: T015 [US1] Crear repositorio de empleados en src/main/java/com/company/empleados/repository/EmpleadoRepository.java
- Task: T016 [US1] Crear DTO de creación en src/main/java/com/company/empleados/dto/EmpleadoCreateRequest.java
- Task: T017 [US1] Crear DTO de respuesta en src/main/java/com/company/empleados/dto/EmpleadoResponse.java

---

## Parallel Example: User Story 2

- Task: T022 [US2] Crear DTO paginado en src/main/java/com/company/empleados/dto/EmpleadoPageResponse.java
- Task: T023 [US2] Consulta por clave en src/main/java/com/company/empleados/service/EmpleadoService.java

---

## Parallel Example: User Story 3

- Task: T029 [US3] Crear DTO de actualización en src/main/java/com/company/empleados/dto/EmpleadoUpdateRequest.java
- Task: T030 [US3] Lógica de actualización en src/main/java/com/company/empleados/service/EmpleadoService.java
- Task: T031 [US3] Lógica de eliminación en src/main/java/com/company/empleados/service/EmpleadoService.java

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Setup y Foundational.
2. Implementar US1 (T014–T021).
3. Validar creación con clave autogenerada `E-<n>` y monotonicidad sin reutilización.
4. Demostrar MVP autenticado con PostgreSQL.

### Incremental Delivery

1. MVP con US1.
2. Agregar US2 con paginación opcional y metadatos.
3. Agregar US3 con update/delete y 404 de inexistentes.
4. Ejecutar fase Polish para contrato, observabilidad y verificación de rendimiento.

### Parallel Team Strategy

1. Equipo A: Setup + Foundational.
2. Equipo B: US1 (servicio/controlador de creación).
3. Equipo C: US2/US3 tras foundation con coordinación sobre capa service.

---

## Notes

- Todas las tareas siguen formato checklist estricto con ID, labels y ruta explícita.
- Las tareas [P] se marcan solo cuando no generan conflicto por dependencia inmediata.
- Contrato OpenAPI y quickstart se tratan como artefactos vivos y se cierran en fase Polish.
