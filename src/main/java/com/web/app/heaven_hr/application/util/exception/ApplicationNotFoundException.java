package com.web.app.heaven_hr.application.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationNotFoundException
 * @since 2019
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {

    /**
     * Desired constructor.
     *
     * @param errorMessage The error message describing the exception.
     */
    public ApplicationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
