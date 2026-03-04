package com.company.empleados.controller;

import com.company.empleados.dto.EmpleadoCreateRequest;
import com.company.empleados.dto.EmpleadoPageResponse;
import com.company.empleados.dto.EmpleadoResponse;
import com.company.empleados.dto.EmpleadoUpdateRequest;
import com.company.empleados.service.EmpleadoService;
import com.company.empleados.validation.ClaveFormatValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(@Valid @RequestBody EmpleadoCreateRequest request) {
        EmpleadoResponse response = empleadoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{clave}")
    public EmpleadoResponse obtenerPorClave(@PathVariable String clave) {
        ClaveFormatValidator.validate(clave);
        return empleadoService.obtenerPorClave(clave);
    }

    @GetMapping
    public Object listar(@RequestParam(required = false) Integer page,
                         @RequestParam(required = false) Integer size) {
        if (page == null && size == null) {
            List<EmpleadoResponse> all = empleadoService.listarTodos();
            return all;
        }

        if (page == null || size == null) {
            throw new IllegalArgumentException("page y size deben enviarse juntos para paginación");
        }

        EmpleadoPageResponse paged = empleadoService.listarPaginado(page, size);
        return paged;
    }

    @PutMapping("/{clave}")
    public EmpleadoResponse actualizar(@PathVariable String clave,
                                       @Valid @RequestBody EmpleadoUpdateRequest request) {
        ClaveFormatValidator.validate(clave);
        return empleadoService.actualizar(clave, request);
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> eliminar(@PathVariable String clave) {
        ClaveFormatValidator.validate(clave);
        empleadoService.eliminar(clave);
        return ResponseEntity.noContent().build();
    }
}
