# Data Model: CRUD de Empleados

## Entity: Empleado

### Purpose
Representa el registro principal del catálogo de empleados.

### Fields
- `clave` (string, PK, required)
  - Constraints:
    - Generada por API en creación
    - Formato obligatorio `E-` + número secuencial
    - Longitud máxima 50
    - Única
    - No nula
    - No vacía
- `nombre` (string, required)
  - Constraints:
    - Longitud máxima 100
    - No nulo
    - No vacío
- `direccion` (string, required)
  - Constraints:
    - Longitud máxima 100
    - No nulo
    - No vacío
- `telefono` (string, required)
  - Constraints:
    - Longitud máxima 100
    - No nulo
    - No vacío

### Relationships
- No aplica (entidad independiente en esta feature).

### Indexing
- PK en `clave`.

## Validation Rules
- Crear empleado:
  - Rechazar si cualquier campo obligatorio llega nulo o vacío.
  - Rechazar si longitudes superan límites (resto > 100).
  - Generar `clave` con patrón `^E-[0-9]+$` en forma secuencial monotónica.
  - No reutilizar claves de empleados eliminados.
- Actualizar empleado:
  - Rechazar con `404` si `clave` no existe.
  - Aplicar mismas reglas de longitud/obligatoriedad para campos editables.
- Eliminar empleado:
  - Rechazar con `404` si `clave` no existe.
- Listar empleados:
  - Si no se reciben parámetros de paginación, devolver listado completo.
  - Si se reciben parámetros de paginación, aplicar tamaño de página fijo de 5.
  - Rechazar parámetros inválidos de paginación (por ejemplo, página negativa).

## State Transitions
- `NoExiste` -> `Creado` (POST exitoso)
- `Creado` -> `Actualizado` (PUT/PATCH exitoso)
- `Creado`/`Actualizado` -> `Eliminado` (DELETE exitoso)
- `NoExiste` + `PUT/DELETE` -> `Error404`

## Persistence Notes
- Motor: PostgreSQL.
- Tabla sugerida: `empleados`.
- Definición de columnas sugerida:
  - `clave VARCHAR(50) PRIMARY KEY`
  - `nombre VARCHAR(100) NOT NULL`
  - `direccion VARCHAR(100) NOT NULL`
  - `telefono VARCHAR(100) NOT NULL`

## Query Notes
- Endpoint de listado con parámetros opcionales de paginación.
- Tamaño de página funcional para esta feature: 5 registros.
- Respuesta paginada con metadatos: `content`, `page`, `size`, `totalElements`, `totalPages`.
