package com.web.app.heaven_hr.offer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote Offer Dto
 * @since 2019
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]*")
    @JsonProperty(required = true, value = "jobTitle")
    private String jobTitle;

    @JsonProperty(value = "startDate")
    private LocalDateTime startDate;

    @JsonProperty(value = "receivedApplications")
    private Long numberOfApplications;

    @JsonProperty(value = "applications")
    private Set<Long> applications;

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setNumberOfApplications(Long numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }

    public void setApplications(Set<Long> applications) {
        this.applications = applications;
    }
}