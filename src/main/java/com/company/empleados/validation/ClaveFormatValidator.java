package com.company.empleados.validation;

import java.util.regex.Pattern;

public final class ClaveFormatValidator {

    private static final Pattern CLAVE_PATTERN = Pattern.compile("^E-[0-9]+$");

    private ClaveFormatValidator() {
    }

    public static void validate(String clave) {
        if (clave == null || clave.isBlank() || clave.length() > 50 || !CLAVE_PATTERN.matcher(clave).matches()) {
            throw new IllegalArgumentException("La clave debe tener formato E-<n> y longitud máxima de 50 caracteres");
        }
    }
}
