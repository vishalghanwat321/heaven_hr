package com.web.app.heaven_hr.offer.domain;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.util.unique_sequence_generator.AbstractRandomLongIdEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote Offer Entity
 * @since 2019
 */

@Entity
@Table(name = "offer")
@Cacheable(false)
public class Offer extends AbstractRandomLongIdEntity {

    @Column(name = "job_title", unique = true, nullable = false)
    private String jobTitle;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime startDate;

    @Column(name = "received_applications", nullable = false)
    private Long receivedApplications;

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id")
    private Set<Application> applications;

    @PrePersist
    protected void onPrePersist() {
        super.onPrePersist();
        this.receivedApplications = 0L;
        this.startDate = LocalDateTime.now(ZoneOffset.UTC);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Long getReceivedApplications() {
        return receivedApplications;
    }

    public void setReceivedApplications(Long receivedApplications) {
        this.receivedApplications = receivedApplications;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}
