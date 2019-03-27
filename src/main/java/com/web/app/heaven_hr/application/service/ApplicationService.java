package com.web.app.heaven_hr.application.service;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.repository.ApplicationRepository;
import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;
import com.web.app.heaven_hr.application.util.exception.ApplicationNotFoundException;
import com.web.app.heaven_hr.application.util.exception.DuplicateApplicationException;
import com.web.app.heaven_hr.handler.notification.NotificationEventHandler;
import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.exception.OfferNotFoundException;
import com.web.app.heaven_hr.offer.service.OfferService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationService
 * @since 2019
 */
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private NotificationEventHandler notificationEventHandler;

    /**
     * @param createApplication
     * @param offerId
     * @return
     * @throws DuplicateApplicationException
     * @throws IllegalArgumentException
     * @throws OfferNotFoundException
     * @throws IllegalStateException
     */
    @Transactional
    public Application create(Application createApplication, Long offerId) throws DuplicateApplicationException, IllegalArgumentException, OfferNotFoundException, IllegalStateException {
        if (StringUtils.isBlank(createApplication.getEmail()) || StringUtils.isBlank(createApplication.getResume()))
            throw new IllegalArgumentException("Invalid request...");
        // find offer
        Offer offer = this.offerService.queryById(offerId);

        // check application & offer combination
        if (this.repository.existsByEmailAndOffer(createApplication.getEmail(), offer))
            throw new DuplicateApplicationException("Application already exists...");

        Application application = new Application();
        application.setOffer(offer);
        application.setEmail(createApplication.getEmail());
        application.setResume(createApplication.getResume());

        //save application
        Application savedApplication = this.repository.save(application);
        if (Objects.isNull(savedApplication))
            throw new IllegalStateException("Failed to save application details...");

        //increment applications count
        this.offerService.increaseNumberOfApplications(offer);

        // post notification to user
        this.notificationEventHandler.pushNotification(savedApplication, null);

        return savedApplication;
    }

    /**
     * @param id
     * @return
     * @throws IllegalArgumentException
     * @throws ApplicationNotFoundException
     */
    @Transactional(readOnly = true)
    public Application queryById(Long id) throws IllegalArgumentException, ApplicationNotFoundException {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("Failed to query application (reason: invalid id)...");
        return this.repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found..."));
    }

    /**
     * @param id
     * @param status
     * @return
     * @throws ApplicationNotFoundException
     * @throws IllegalStateException
     */
    @Transactional
    public Application updateApplicationStatus(Long id, ApplicationStatus status) throws ApplicationNotFoundException, IllegalStateException {
        Application application = this.queryById(id);

        // prev status to notify the change to user
        ApplicationStatus prevStatus = application.getStatus();

        application.setStatus(status);

        //update application
        Application updatedApplication = this.repository.save(application);
        if (Objects.isNull(updatedApplication))
            throw new IllegalStateException("Failed to update application details...");

        // post notification to user
        this.notificationEventHandler.pushNotification(updatedApplication, prevStatus);
        return updatedApplication;
    }
}
