package com.company.empleados.service;

import com.company.empleados.config.ResourceNotFoundException;
import com.company.empleados.dto.EmpleadoCreateRequest;
import com.company.empleados.dto.EmpleadoPageResponse;
import com.company.empleados.dto.EmpleadoResponse;
import com.company.empleados.dto.EmpleadoUpdateRequest;
import com.company.empleados.entity.Empleado;
import com.company.empleados.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoClaveGenerator claveGenerator;

    public EmpleadoService(EmpleadoRepository empleadoRepository, EmpleadoClaveGenerator claveGenerator) {
        this.empleadoRepository = empleadoRepository;
        this.claveGenerator = claveGenerator;
    }

    @Transactional
    public EmpleadoResponse crear(EmpleadoCreateRequest request) {
        Empleado empleado = new Empleado();
        empleado.setClave(claveGenerator.nextClave());
        empleado.setNombre(request.nombre());
        empleado.setDireccion(request.direccion());
        empleado.setTelefono(request.telefono());
        Empleado saved = empleadoRepository.save(empleado);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse obtenerPorClave(String clave) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con clave " + clave));
        return toResponse(empleado);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponse> listarTodos() {
        return empleadoRepository.findAll(Sort.by(Sort.Direction.ASC, "clave"))
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public EmpleadoPageResponse listarPaginado(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page debe ser mayor o igual a 0");
        }
        if (size != 5) {
            throw new IllegalArgumentException("size debe ser igual a 5");
        }

        Page<Empleado> result = empleadoRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "clave")));
        return new EmpleadoPageResponse(
            result.getContent().stream().map(this::toResponse).toList(),
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.getTotalPages());
    }

    @Transactional
    public EmpleadoResponse actualizar(String clave, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con clave " + clave));
        empleado.setNombre(request.nombre());
        empleado.setDireccion(request.direccion());
        empleado.setTelefono(request.telefono());
        Empleado updated = empleadoRepository.save(empleado);
        return toResponse(updated);
    }

    @Transactional
    public void eliminar(String clave) {
        Empleado empleado = empleadoRepository.findById(clave)
            .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con clave " + clave));
        empleadoRepository.delete(empleado);
    }

    private EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(
            empleado.getClave(),
            empleado.getNombre(),
            empleado.getDireccion(),
            empleado.getTelefono());
    }
}
