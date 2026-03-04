# Feature Specification: CRUD de Empleados

**Feature Branch**: `001-crud-empleados`  
**Created**: 2026-02-25  
**Status**: Draft  
**Input**: User description: "crear un crud de empleados con los campos clave, nombre, direccion y telefono. Donde clave sea pk, y los demas campos sean de 100 espacios"

## Clarifications

### Session 2026-02-25

- Q: ¿Qué tipo de dato debe tener `clave` como PK? → A: `clave` tipo texto (`VARCHAR`), PK única.
- Q: ¿Cuál es la longitud máxima de `clave`? → A: `clave` alfanumérica con máximo 50 caracteres.
- Q: ¿Qué campos son obligatorios? → A: `nombre`, `direccion` y `telefono` son obligatorios.
- Q: ¿Qué código HTTP devolver en update/delete de clave inexistente? → A: `404 Not Found`.
- Q: ¿La consulta general debe paginarse? → A: paginación opcional; si no se envían parámetros devuelve todo, y si se pagina devuelve bloques de 5 registros.
- Q: ¿Qué formato debe tener la clave PK? → A: formato mixto `E-` + número secuencial.
- Q: ¿Quién genera la clave secuencial? → A: la API genera automáticamente la clave `E-<n>` al crear.
- Q: ¿La secuencia de clave reutiliza valores eliminados? → A: secuencia monotónica global sin reutilizar claves eliminadas.
- Q: ¿Qué formato de respuesta usa el listado paginado? → A: objeto paginado con `content`, `page`, `size`, `totalElements`, `totalPages`.

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Registrar empleado (Priority: P1)

Como usuario autenticado, quiero registrar un empleado con nombre, dirección y
teléfono para contar con un alta inicial válida en el catálogo.

**Why this priority**: Sin alta de empleados no existe información base para
el resto del ciclo CRUD.

**Independent Test**: Puede probarse de forma aislada creando empleados nuevos
con datos válidos y verificando persistencia y respuesta de confirmación.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado, **When** registra un empleado con nombre,
  dirección y teléfono de hasta 100 caracteres, **Then** el sistema guarda el
  empleado, genera una clave secuencial en formato `E-<n>` y confirma creación
  exitosa.
2. **Given** un usuario autenticado, **When** intenta registrar un empleado con
  datos inválidos, **Then** el sistema rechaza la operación e informa los
  errores de validación.

---

### User Story 2 - Consultar empleados (Priority: P2)

Como usuario autenticado, quiero consultar uno o varios empleados para revisar
la información registrada en el catálogo.

**Why this priority**: La consulta permite explotar el valor del dato cargado,
validar registros y soportar operaciones administrativas.

**Independent Test**: Puede probarse independientemente listando empleados y
consultando por clave para verificar que los datos devueltos son correctos.

**Acceptance Scenarios**:

1. **Given** empleados previamente registrados, **When** el usuario solicita la
  lista sin parámetros de paginación, **Then** el sistema devuelve todos los
  empleados existentes.
2. **Given** empleados previamente registrados, **When** el usuario solicita la
  lista con parámetros de paginación, **Then** el sistema devuelve una página
  de 5 registros en un objeto paginado con metadatos.
3. **Given** una clave existente, **When** el usuario consulta por clave,
  **Then** el sistema devuelve los datos del empleado correspondiente.
4. **Given** una clave inexistente, **When** el usuario consulta por clave,
  **Then** el sistema informa que el empleado no existe.

---

### User Story 3 - Actualizar y eliminar empleado (Priority: P3)

Como usuario autenticado, quiero modificar o eliminar empleados para mantener
el catálogo actualizado y sin registros obsoletos.

**Why this priority**: Completa el ciclo de mantenimiento del catálogo, pero
depende de que el alta y la consulta ya funcionen correctamente.

**Independent Test**: Puede probarse de forma independiente actualizando y
eliminando un empleado existente y verificando el resultado posterior.

**Acceptance Scenarios**:

1. **Given** un empleado existente, **When** el usuario actualiza nombre,
   dirección o teléfono con valores válidos, **Then** el sistema persiste los
   cambios y confirma actualización exitosa.
2. **Given** un empleado existente, **When** el usuario lo elimina,
   **Then** el sistema quita el registro y ya no lo devuelve en consultas.

---

### Edge Cases

<!--
  ACTION REQUIRED: The content in this section represents placeholders.
  Fill them out with the right edge cases.
-->

- Intentar registrar o actualizar nombre, dirección o teléfono con más de 100
  caracteres.
- Intentar consultar, actualizar o eliminar usando una `clave` con más de 50
  caracteres.
- Intentar consultar, actualizar o eliminar usando una `clave` con formato
  distinto a `E-` seguido de número secuencial.
- Intentar registrar un empleado con campos obligatorios vacíos.
- Intentar registrar o actualizar con `nombre`, `direccion` o `telefono` como
  `null` o cadena vacía.
- Intentar actualizar o eliminar una clave que no existe.
- Al actualizar o eliminar una clave inexistente, la API debe responder con
  `404 Not Found`.
- Intentar crear dos empleados con la misma clave en solicitudes concurrentes.
- Tras eliminar un empleado, crear uno nuevo no debe reutilizar la clave eliminada.
- Intentar acceder al CRUD sin credenciales válidas.
- Solicitar paginación con parámetros inválidos (por ejemplo página negativa).

## Requirements *(mandatory)*

<!--
  ACTION REQUIRED: The content in this section represents placeholders.
  Fill them out with the right functional requirements.
-->

### Functional Requirements

- **FR-001**: El sistema MUST permitir crear empleados con los campos
  `nombre`, `direccion` y `telefono`.
- **FR-001a**: Al crear un empleado, el sistema MUST generar automáticamente
  la `clave` única en formato `E-` + número secuencial.
- **FR-001b**: La generación secuencial de `clave` MUST ser monotónica global
  y no MUST reutilizar claves eliminadas.
- **FR-002**: El campo `clave` MUST ser único y actuar como identificador
  primario del empleado.
- **FR-003**: El campo `clave` MUST aceptar un máximo de 50 caracteres.
- **FR-004**: Los campos `nombre`, `direccion` y `telefono` MUST aceptar un
  máximo de 100 caracteres cada uno.
- **FR-004a**: Los campos `nombre`, `direccion` y `telefono` MUST ser
  obligatorios (no `null` y no vacíos).
- **FR-005**: El sistema MUST permitir consultar todos los empleados y
  consultar un empleado por `clave`.
- **FR-005a**: La consulta general de empleados MUST soportar paginación
  opcional.
- **FR-005b**: Si la consulta general no recibe parámetros de paginación, MUST
  devolver el listado completo.
- **FR-005c**: Si la consulta general recibe parámetros de paginación, MUST
  devolver resultados en páginas de 5 registros.
- **FR-005d**: Cuando la consulta paginada se use, la respuesta MUST incluir
  `content`, `page`, `size`, `totalElements` y `totalPages`.
- **FR-006**: El sistema MUST permitir actualizar `nombre`, `direccion` y
  `telefono` de un empleado existente identificado por `clave`.
- **FR-007**: El sistema MUST permitir eliminar un empleado existente por
  `clave`.
- **FR-007a**: El sistema MUST responder con `404 Not Found` cuando se intente
  actualizar o eliminar un empleado con `clave` inexistente.
- **FR-008**: El sistema MUST rechazar operaciones con datos inválidos
  (campos obligatorios vacíos, longitudes fuera de rango, clave duplicada o
  formato de clave inválido).
- **FR-009**: El sistema MUST requerir autenticación HTTP Basic para todas las
  operaciones del CRUD.
- **FR-010**: El sistema MUST registrar eventos de creación, actualización y
  eliminación para trazabilidad operativa.

### Constitution Alignment *(mandatory)*

- **CA-001**: La feature MUST ejecutarse en Java 17 con Spring Boot 3.x.
- **CA-002**: La feature MUST aplicar autenticación HTTP Basic para el CRUD,
  con credenciales locales `admin`/`admin123` y override por variables de
  entorno en ambientes no locales.
- **CA-003**: La feature MUST persistir en PostgreSQL e incluir definición de
  estructura de datos para empleados con `clave` primaria.
- **CA-004**: La feature MUST declarar ejecución en contenedores Docker para la
  API y su dependencia PostgreSQL.
- **CA-005**: La feature MUST exponer y mantener documentación OpenAPI/Swagger
  para todas las operaciones del CRUD de empleados.

## Assumptions

- El CRUD será consumido por usuarios administrativos autenticados.
- La API generará automáticamente `clave` con formato mixto `E-` + número
  secuencial.
- No se incluyen reglas de formato avanzadas para teléfono en esta entrega;
  solo obligatoriedad y límite de longitud.
- No se contempla borrado lógico; la eliminación es física del registro.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa un registro administrativo de personal. Atributos:
  `clave` (identificador primario generado por sistema con formato `E-` +
  número secuencial),
  `nombre`, `direccion`, `telefono`.

## Success Criteria *(mandatory)*

<!--
  ACTION REQUIRED: Define measurable success criteria.
  These must be technology-agnostic and measurable.
-->

### Measurable Outcomes

- **SC-001**: El 100% de operaciones de creación válidas persiste empleados con
  `clave` única generada por el sistema con formato `E-` + número secuencial
  y campos visibles en consultas posteriores.
- **SC-001a**: En secuencias de creación con eliminaciones intermedias,
  el sistema mantiene monotonicidad y no reutiliza claves previamente asignadas.
- **SC-002**: El 100% de intentos con `nombre`, `direccion` o `telefono` de más
  de 100 caracteres es rechazado con mensaje de validación.
- **SC-003**: Al menos 95% de operaciones CRUD responden en menos de 2 segundos
  en condiciones de uso normal del entorno de desarrollo.
- **SC-004**: El 100% de endpoints CRUD quedan accesibles y verificables desde
  la documentación Swagger durante pruebas funcionales.
