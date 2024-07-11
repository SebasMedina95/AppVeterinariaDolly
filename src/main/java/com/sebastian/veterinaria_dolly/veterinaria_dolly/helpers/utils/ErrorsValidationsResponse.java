package com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ErrorsValidationsResponse {

    public Object validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        return errors;

    }

}
