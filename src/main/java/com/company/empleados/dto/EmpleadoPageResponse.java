package com.company.empleados.dto;

import java.util.List;

public record EmpleadoPageResponse(
    List<EmpleadoResponse> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {
}
