package com.web.app.heaven_hr.handler.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationException custom exception.
 * @since 2019
 */
public class ApplicationException {

    private LocalDateTime dateTime;
    private String message;
    private List<String> errors;

    public ApplicationException(LocalDateTime dateTime, String message, List<String> errors) {
        super();
        this.dateTime = dateTime;
        this.message = message;
        this.errors = errors;
    }

    public ApplicationException(LocalDateTime dateTime, String message, String error) {
        super();
        this.dateTime = dateTime;
        this.message = message;
        errors = Arrays.asList(error);
    }
}