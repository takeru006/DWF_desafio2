package com.example.demo.exception;

public class ApiError {
    private String type;
    private String source;
    private String description;
    private String title;
    private int status;

    // Métodos de establecimiento (setters) que devuelven 'this' para permitir encadenamiento
    public ApiError setType(String type) {
        this.type = type;
        return this; // Devuelve la instancia actual
    }

    public ApiError setSource(String source) {
        this.source = source;
        return this; // Devuelve la instancia actual
    }

    public ApiError setDescription(String description) {
        this.description = description;
        return this; // Devuelve la instancia actual
    }

    public ApiError setTitle(String title) {
        this.title = title;
        return this; // Devuelve la instancia actual
    }

    public ApiError setStatus(int status) {
        this.status = status;
        return this; // Devuelve la instancia actual
    }

    // Método estático para crear una nueva instancia de ApiError
    public static ApiError createApiError() {
        return new ApiError();
    }

    // Getters (opcional)
    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public ApiError build() {
        return null;
    }
}