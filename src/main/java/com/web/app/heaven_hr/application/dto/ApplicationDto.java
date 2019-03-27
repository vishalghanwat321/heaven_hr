package com.web.app.heaven_hr.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationDto
 * @since 2019
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JsonProperty(value = "offer", required = true)
    private Long offerId;

    @NotNull
    @Email
    @JsonProperty(required = true, value = "candidateEmail")
    private String candidateEmail;

    @NotNull
    @Size(min = 1, max = 4194304)  //maximum 4 mb allowed
    @JsonProperty(required = true, value = "resume")
    private String resume;

    @JsonProperty(value = "status")
    private ApplicationStatus applicationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}