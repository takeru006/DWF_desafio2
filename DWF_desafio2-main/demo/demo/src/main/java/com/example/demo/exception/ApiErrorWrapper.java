package com.example.demo.exception;

import java.util.ArrayList;
import java.util.List;

public class ApiErrorWrapper {
    private List<ApiError> errors = new ArrayList<>();

    public void addApiError(ApiError error) {
        errors.add(error);
    }

    public void addFieldError(String errorType, String attribute, String field, String message) {
        ApiError fieldError = ApiError.createApiError(); // Usando el método estático para crear una instancia
        fieldError.setType(errorType);
        fieldError.setSource(attribute);
        fieldError.setDescription(message);
        fieldError.setTitle("Field Error: " + field);
        fieldError.setStatus(400); // Puedes ajustar el código de estado según sea necesario
        errors.add(fieldError);
    }

    public List<ApiError> getErrors() {
        return errors;
    }
}