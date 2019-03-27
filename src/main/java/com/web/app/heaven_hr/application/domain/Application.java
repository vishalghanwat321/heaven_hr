package com.web.app.heaven_hr.application.domain;

import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;
import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.util.unique_sequence_generator.AbstractRandomLongIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote Application entity
 * @since 2019
 */
@Entity
@Table(name = "application", uniqueConstraints = @UniqueConstraint(columnNames = {"offer", "email"}))
@Cacheable(false)
public class Application extends AbstractRandomLongIdEntity {

    @JoinColumn(name = "offer", updatable = false)
    @ManyToOne
    private Offer offer;

    @Email
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "resume", length = 4194304, nullable = false)  //maximum 4 mb allowed
    private String resume;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    public Offer getOffer() {
        return offer;
    }

    @PrePersist
    protected void onPrePersist() {
        super.onPrePersist();
        this.status = ApplicationStatus.APPLIED;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
