package com.web.app.heaven_hr.application.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote DuplicateApplicationException
 * @since 2019
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateApplicationException extends RuntimeException {
    /**
     * Desired constructor.
     *
     * @param errorMessage The error message describing the exception.
     */
    public DuplicateApplicationException(String errorMessage) {
        super(errorMessage);
    }
}