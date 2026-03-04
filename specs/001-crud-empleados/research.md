# Phase 0 Research: CRUD de Empleados

## Decision 1: Tipo de `clave` en Empleado
- Decision: Usar `clave` como `VARCHAR(50)` generado por API, formato obligatorio `E-` + número secuencial, llave primaria.
- Rationale: Evita colisiones en clientes, centraliza reglas de negocio y asegura consistencia del identificador.
- Alternatives considered:
  - `BIGINT` autoincremental: simplifica generación pero pierde el prefijo de negocio requerido.
  - Clave provista por cliente: incrementa riesgo de colisiones y validaciones complejas.

## Decision 1a: Política de secuencial
- Decision: La secuencia de `clave` es monotónica global y no reutiliza valores eliminados.
- Rationale: Mantiene trazabilidad histórica y simplifica concurrencia.
- Alternatives considered:
  - Reutilizar huecos: reduce crecimiento numérico, pero complica consistencia y auditoría.
  - Reiniciar por entorno/despliegue: rompe continuidad semántica.

## Decision 2: Reglas de obligatoriedad de campos
- Decision: `nombre`, `direccion` y `telefono` obligatorios, cada uno con longitud máxima 100.
- Rationale: Evita registros incompletos y estandariza la calidad mínima del catálogo.
- Alternatives considered:
  - Campos opcionales: reduce fricción inicial pero degrada calidad de datos.
  - Solo `telefono` opcional: introduce excepciones innecesarias para el MVP.

## Decision 3: Comportamiento HTTP para recursos inexistentes
- Decision: Responder `404 Not Found` al actualizar o eliminar una `clave` inexistente.
- Rationale: Contrato explícito, predecible para consumidores y fácil de validar en pruebas.
- Alternatives considered:
  - `204 No Content`: idempotente pero oculta errores de referencia de recurso.
  - `400 Bad Request`: semánticamente menos preciso para recurso no encontrado.

## Decision 4: Estrategia de listado
- Decision: Paginación opcional; sin parámetros devuelve listado completo, con parámetros devuelve páginas de 5 registros.
- Rationale: Mantiene simplicidad del uso básico y habilita control de volumen cuando el consumidor lo requiere.
- Alternatives considered:
  - Sin paginación siempre: simple, pero sin control de tamaño de respuesta.
  - Paginación obligatoria: impone complejidad para casos de uso pequeños.

## Decision 4a: Forma de respuesta paginada
- Decision: La respuesta paginada retorna objeto con `content`, `page`, `size`, `totalElements`, `totalPages`.
- Rationale: Define un contrato estable y testeable para consumidores.
- Alternatives considered:
  - Arreglo simple siempre: ambiguo para metadatos de navegación.
  - Encabezados HTTP + arreglo: menos explícito para clientes básicos.

## Decision 5: Stack y prácticas para persistencia/seguridad
- Decision: Spring Boot 3 + Java 17 + Spring Security Basic + Spring Data JPA + PostgreSQL.
- Rationale: Alineación total con la constitución del proyecto y rápida implementación del CRUD.
- Alternatives considered:
  - Frameworks alternativos backend: incumplen la constitución.
  - Persistencia no relacional: no alineada con mandato de PostgreSQL.

## Decision 6: Contenedores y documentación de contrato
- Decision: Ejecutar app y PostgreSQL con Docker Compose local y documentar endpoints con OpenAPI/Swagger.
- Rationale: Reproducibilidad de entorno y contrato verificable para equipos consumidores.
- Alternatives considered:
  - Ejecución local sin contenedores: mayor variabilidad de entorno.
  - Documentación solo textual: menor trazabilidad y validación de contrato.

## Resolution of Technical Context Unknowns
No quedan elementos `NEEDS CLARIFICATION` para esta feature. El contexto técnico está totalmente definido para pasar a diseño (Phase 1).
