package com.company.empleados.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoClaveGenerator {

    private final JdbcTemplate jdbcTemplate;

    public EmpleadoClaveGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String nextClave() {
        Long value = jdbcTemplate.queryForObject("SELECT nextval('empleado_seq')", Long.class);
        if (value == null) {
            throw new IllegalStateException("No se pudo generar la clave de empleado");
        }
        return "E-" + value;
    }
}
