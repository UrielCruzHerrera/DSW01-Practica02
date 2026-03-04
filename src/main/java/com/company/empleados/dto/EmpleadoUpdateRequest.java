package com.company.empleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpleadoUpdateRequest(
    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre máximo 100 caracteres")
    String nombre,

    @NotBlank(message = "direccion es obligatoria")
    @Size(max = 100, message = "direccion máximo 100 caracteres")
    String direccion,

    @NotBlank(message = "telefono es obligatorio")
    @Size(max = 100, message = "telefono máximo 100 caracteres")
    String telefono
) {
}
