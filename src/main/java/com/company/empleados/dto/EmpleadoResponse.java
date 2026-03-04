package com.company.empleados.dto;

public record EmpleadoResponse(
    String clave,
    String nombre,
    String direccion,
    String telefono
) {
}
