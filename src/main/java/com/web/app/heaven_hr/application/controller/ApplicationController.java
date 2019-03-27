package com.web.app.heaven_hr.application.controller;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.dto.ApplicationDto;
import com.web.app.heaven_hr.application.service.ApplicationService;
import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;
import com.web.app.heaven_hr.application.util.exception.ApplicationNotFoundException;
import com.web.app.heaven_hr.application.util.exception.DuplicateApplicationException;
import com.web.app.heaven_hr.application.util.mapper.ApplicationDtoToApplicationMapper;
import com.web.app.heaven_hr.application.util.mapper.ApplicationToApplicationDtoMapper;
import com.web.app.heaven_hr.offer.exception.OfferNotFoundException;
import com.web.app.heaven_hr.util.constants.ApiPathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationController will expose endpoints for application
 * @since 2019
 */
@RestController
@RequestMapping(ApiPathConstants.APPLICATION)
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @Autowired
    private ApplicationToApplicationDtoMapper applicationToApplicationDtoMapper;

    @Autowired
    private ApplicationDtoToApplicationMapper applicationDtoToApplicationMapper;

    /**
     * @param applicationDto
     * @return
     * @throws DuplicateApplicationException
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ApplicationDto createOffer(@Valid @RequestBody ApplicationDto applicationDto) throws DuplicateApplicationException {
        if (Objects.isNull(applicationDto.getOfferId()))
            throw new IllegalArgumentException("Failed to create application (reason: invalid offer)...");
        Long offerId = applicationDto.getOfferId();
        Application application = this.applicationDtoToApplicationMapper.convert(applicationDto);
        Application createdApplication = this.service.create(application, offerId);
        return this.applicationToApplicationDtoMapper.convert(createdApplication);
    }

    /**
     * @param applicationId
     * @return
     * @throws OfferNotFoundException
     */
    @GetMapping(value = "/{applicationId:[0-9]+}")
    @ResponseBody
    public ApplicationDto findApplicationById(@PathVariable("applicationId") Long applicationId) throws ApplicationNotFoundException {
        Application application = this.service.queryById(applicationId);
        return this.applicationToApplicationDtoMapper.convert(application);
    }

    /**
     * @param applicationId
     * @param applicationStatus
     * @return
     * @throws ApplicationNotFoundException
     * @throws IllegalArgumentException
     */
    @PutMapping("/{applicationId:[0-9]+}")
    @ResponseBody
    public ApplicationDto updateApplicationStatus(@PathVariable("applicationId") Long applicationId,
                                                  @RequestParam("status") ApplicationStatus applicationStatus) throws ApplicationNotFoundException, IllegalArgumentException {
        Application application = this.service.updateApplicationStatus(applicationId, applicationStatus);
        return this.applicationToApplicationDtoMapper.convert(application);
    }

}