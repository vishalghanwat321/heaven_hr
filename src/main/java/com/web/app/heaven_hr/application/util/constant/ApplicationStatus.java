package com.web.app.heaven_hr.application.util.constant;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This is for application status, for now we are supporting APPLIED, INVITED, REJECTED & HIRED.
 * we can add more status here as per need.
 * @since 2019
 */

public enum ApplicationStatus {
    APPLIED("APPLIED"),
    INVITED("INVITED"),
    REJECTED("REJECTED"),
    HIRED("HIRED");

    String status;

    ApplicationStatus(String status) {
        this.status = status;
    }
}