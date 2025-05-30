package com.example.demo.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Manejo de errores de validación
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiErrorWrapper apiErrorWrapper = processErrors(ex.getBindingResult().getAllErrors());
        return handleExceptionInternal(ex, apiErrorWrapper, headers, HttpStatus.BAD_REQUEST, request);
    }

    // Manejo de HttpClientErrorException
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex, WebRequest request) {
        return createResponseEntity(ex, new HttpHeaders(), ex.getStatusCode(), request);
    }

    // Manejo de ValidationException
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidation(ValidationException ex, WebRequest request) {
        ApiErrorWrapper apiErrors = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Manejo de AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // Manejo de EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // Manejo de DataAccessException
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccess(DataAccessException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // Manejo de IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleInvalidMimeType(IllegalArgumentException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Manejo de Exception general
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handle500Exception(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Métodos de utilidad
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (Objects.isNull(body)) {
            ApiErrorWrapper apiErrors = message(status, ex);
            return new ResponseEntity<>(apiErrors, headers, status);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    // Construir ApiError DTO
    protected ApiErrorWrapper message(HttpStatus httpStatus, Exception ex) {
        return message(buildApiError(httpStatus, ex));
    }

    protected ApiErrorWrapper message (final ApiError error) {
        ApiErrorWrapper errors = new ApiErrorWrapper();
        errors.addApiError(error);
        return errors;
    }

    protected ApiErrorWrapper processErrors(List<ObjectError> errors) {
        ApiErrorWrapper dto = new ApiErrorWrapper();
        errors.forEach(objError -> {
            if (isFieldError(objError)) {
                FieldError fieldError = (FieldError) objError;
                String localizedErrorMessage = fieldError.getDefaultMessage();
                dto.addFieldError(fieldError.getClass().getSimpleName(), "Invalid Attribute", fieldError.getField(), localizedErrorMessage);
            } else {
                String localizedErrorMessage = objError.getDefaultMessage();
                dto.addFieldError(objError.getClass().getSimpleName(), "Invalid Attribute", "base", localizedErrorMessage);
            }
        });
        return dto;
    }

    private ApiError buildApiError(HttpStatus httpStatus, Exception ex) {
        String typeException = ex.getClass().getSimpleName();
        String description = StringUtils.defaultIfBlank(ex.getMessage(), ex.getClass().getSimpleName());
        String source = "base";
        if (isMissingRequestParameterException(ex)) {
            MissingServletRequestParameterException missingParamEx = (MissingServletRequestParameterException) ex;
            source = missingParamEx.getParameterName();
            return ApiError.createApiError()
                    .setStatus(httpStatus.value())
                    .setType(typeException)
                    .setTitle(httpStatus.getReasonPhrase())
                    .setDescription(description)
                    .setSource(source)
                    .build();
        } else {
            if (isMissingPathVariableException(ex)) {
                MissingPathVariableException missingPathEx = (MissingPathVariableException) ex;
                source = missingPathEx.getVariableName();
            }
            return ApiError.createApiError()
                    .setStatus(httpStatus.value())
                    .setType(typeException)
                    .setTitle(httpStatus.getReasonPhrase())
                    .setDescription(description)
                    .setSource(source)
                    .build();
        }
    }

    private boolean isMissingPathVariableException(Exception ex) {
        return ex instanceof MissingPathVariableException;
    }

    private boolean isMissingRequestParameterException(Exception ex) {
        return ex instanceof MissingServletRequestParameterException;
    }

    private boolean isFieldError(ObjectError objError) {
        return objError instanceof FieldError;
    }
}