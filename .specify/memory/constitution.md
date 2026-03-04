<!--
Sync Impact Report
- Version change: 0.0.0 -> 1.0.0
- Modified principles:
	- Principle 1 placeholder -> I. Stack Backend Inmutable
	- Principle 2 placeholder -> II. Seguridad HTTP Basic Obligatoria
	- Principle 3 placeholder -> III. Persistencia PostgreSQL y Acceso a Datos
	- Principle 4 placeholder -> IV. Contenerización Docker First
	- Principle 5 placeholder -> V. Contrato API y Documentación Swagger
- Added sections:
	- Normas Técnicas Obligatorias
	- Flujo de Desarrollo y Puertas de Calidad
- Removed sections:
	- Ninguna
- Templates requiring updates:
	- ✅ .specify/templates/plan-template.md
	- ✅ .specify/templates/spec-template.md
	- ✅ .specify/templates/tasks-template.md
	- ⚠ pending: .specify/templates/commands/*.md (directorio no existe en el repositorio)
	- ⚠ pending: README.md / docs/quickstart.md (no existen en el repositorio)
- Deferred TODOs:
	- Ninguno
-->

# DSW01-Practica02 Constitution

## Core Principles

### I. Stack Backend Inmutable
Todo entregable MUST implementarse sobre Java 17 y Spring Boot 3.x. Se prohíbe
introducir runtimes o frameworks backend alternativos para lógica principal sin
enmienda formal de esta constitución. Rationale: uniformidad tecnológica,
mantenimiento predecible y menor costo operativo.

### II. Seguridad HTTP Basic Obligatoria
Toda ruta HTTP expuesta por la API MUST requerir autenticación HTTP Basic,
excepto endpoints explícitamente públicos aprobados en la especificación de la
feature. Para entorno local por defecto, las credenciales MUST ser
`admin`/`admin123`; para ambientes no locales, las credenciales MUST inyectarse
por variables de entorno y no codificarse en código fuente. Rationale: asegurar
un baseline de control de acceso y facilitar pruebas consistentes.

### III. Persistencia PostgreSQL y Acceso a Datos
La persistencia transaccional MUST residir en PostgreSQL. Toda configuración de
acceso a datos MUST usar propiedades externas (`application.properties` y/o
variables de entorno), y toda feature que altere esquema MUST incluir estrategia
de migración versionada. Rationale: consistencia de datos y trazabilidad de
cambios de esquema.

### IV. Contenerización Docker First
El servicio backend y sus dependencias de runtime (mínimo PostgreSQL) MUST
ejecutarse mediante Docker para validación local y despliegue reproducible.
Todo cambio de infraestructura MUST mantener compatibilidad con ejecución vía
contenedores. Rationale: paridad entre desarrollo y despliegue.

### V. Contrato API y Documentación Swagger
Toda API REST MUST estar documentada con OpenAPI/Swagger y sincronizada con el
comportamiento real del código. Ningún endpoint nuevo o modificado se considera
completo sin evidencia de documentación accesible y actualizada. Rationale:
reduce fricción de integración y defectos por contratos implícitos.

## Normas Técnicas Obligatorias

- El backend MUST exponer health checks y logs estructurados para diagnóstico.
- La configuración sensible (usuarios, contraseñas, URLs) MUST provenir de
	variables de entorno en ambientes compartidos o productivos.
- El acceso a PostgreSQL MUST parametrizar host, puerto, base, usuario y
	contraseña mediante propiedades configurables.
- Swagger UI MUST permanecer disponible para equipos de desarrollo en entorno
	local y de integración.

## Flujo de Desarrollo y Puertas de Calidad

1. Toda especificación (`spec.md`) MUST declarar impacto en seguridad,
	 persistencia y contrato API.
2. Todo plan (`plan.md`) MUST pasar Constitution Check antes de implementar.
3. Toda implementación MUST incluir:
	 - validación de autenticación HTTP Basic,
	 - prueba de conectividad y operaciones con PostgreSQL,
	 - verificación de ejecución en contenedor,
	 - verificación de endpoints en Swagger/OpenAPI.
4. Ningún cambio se considera listo sin revisión que confirme cumplimiento de
	 principios y normas técnicas.

## Governance

Esta constitución prevalece sobre prácticas ad hoc del proyecto.

- Enmiendas MUST incluir: propuesta escrita, impacto en plantillas de
	`.specify/templates`, plan de migración y fecha de entrada en vigor.
- Política de versionado constitucional (SemVer):
	- MAJOR: eliminación o redefinición incompatible de principios o gobernanza.
	- MINOR: adición de principios/secciones o expansión normativa material.
	- PATCH: aclaraciones editoriales sin cambio semántico.
- Compliance review MUST ejecutarse en cada PR, verificando evidencia explícita
	de cumplimiento para seguridad, PostgreSQL, Docker y Swagger.
- Si una plantilla o documento de runtime no existe, se registra como pendiente
	en el Sync Impact Report y MUST resolverse en el siguiente ciclo documental.

**Version**: 1.0.0 | **Ratified**: 2026-02-25 | **Last Amended**: 2026-02-25
