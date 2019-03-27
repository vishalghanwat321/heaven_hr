package com.web.app.heaven_hr.offer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote DuplicateOfferException
 * @since 2019
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateOfferException extends RuntimeException {

    /**
     * Desired constructor.
     *
     * @param errorMessage The error message describing the exception.
     */
    public DuplicateOfferException(String errorMessage) {
        super(errorMessage);
    }
}
