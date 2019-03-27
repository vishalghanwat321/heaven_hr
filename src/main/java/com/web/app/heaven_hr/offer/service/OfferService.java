package com.web.app.heaven_hr.offer.service;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.util.exception.ApplicationNotFoundException;
import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.exception.DuplicateOfferException;
import com.web.app.heaven_hr.offer.exception.OfferNotFoundException;
import com.web.app.heaven_hr.offer.repository.OfferRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote OfferService
 * @since 2019
 */
@Service
public class OfferService {

    @Autowired
    private OfferRepository repository;

    /**
     * @param specification
     * @return
     */
    @Transactional(readOnly = true)
    public Iterable<Offer> query(Specification<Offer> specification) {
        return this.repository.findAll(specification);
    }

    /**
     * @param specification
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Offer> query(Specification<Offer> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    /**
     * @param createOffer
     * @return
     * @throws DuplicateOfferException
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Transactional
    public Offer create(Offer createOffer) throws DuplicateOfferException, IllegalArgumentException, IllegalStateException {
        if (StringUtils.isBlank(createOffer.getJobTitle()))
            throw new IllegalArgumentException("Invalid job title...");
        // check is the offer already exists
        if (this.repository.existsByJobTitleIgnoreCase(createOffer.getJobTitle()))
            throw new DuplicateOfferException("Offer already exists...");
        Offer offer = new Offer();
        offer.setJobTitle(createOffer.getJobTitle());
        Offer savedOffer = this.repository.save(offer);
        if (Objects.isNull(savedOffer))
            throw new IllegalStateException("Failed to save details...");
        return savedOffer;
    }

    /**
     * @param id
     * @return
     * @throws IllegalArgumentException
     * @throws OfferNotFoundException
     */
    @Transactional(readOnly = true)
    public Offer queryById(Long id) throws IllegalArgumentException, OfferNotFoundException {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("Failed to query Offer (reason: invalid id)...");
        return this.repository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found..."));
    }

    /**
     * @param id
     * @return
     * @throws OfferNotFoundException
     */
    @Transactional(readOnly = true)
    public Set<Application> findApplicationsLinkedToOffer(Long id) throws OfferNotFoundException {
        Offer offer = this.queryById(id);
        return offer.getApplications();
    }

    /**
     * @param offerId
     * @param applicationId
     * @return
     * @throws OfferNotFoundException
     * @throws ApplicationNotFoundException
     */
    @Transactional(readOnly = true)
    public Application findApplicationLinkedToOffer(Long offerId, Long applicationId) throws OfferNotFoundException, ApplicationNotFoundException {
        Offer offer = this.queryById(offerId);
        return offer.getApplications()
                .stream()
                .filter(Objects::nonNull)
                .filter(application1 -> application1.getId().equals(applicationId))
                .findFirst().orElseThrow(() -> new ApplicationNotFoundException("Application not found..."));
    }

    /**
     * @param offer
     * @return
     * @throws OfferNotFoundException
     * @throws IllegalStateException
     */
    @Transactional
    public Offer increaseNumberOfApplications(Offer offer) throws OfferNotFoundException, IllegalStateException {
        this.queryById(offer.getId());
        offer.setReceivedApplications(offer.getReceivedApplications() + 1);
        Offer updatedOffer = this.repository.save(offer);
        if (Objects.isNull(updatedOffer))
            throw new IllegalStateException("Failed to update details...");
        return updatedOffer;
    }
}
