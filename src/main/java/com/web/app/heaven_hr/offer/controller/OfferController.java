package com.web.app.heaven_hr.offer.controller;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.dto.ApplicationDto;
import com.web.app.heaven_hr.application.util.exception.ApplicationNotFoundException;
import com.web.app.heaven_hr.application.util.mapper.ApplicationToApplicationDtoMapper;
import com.web.app.heaven_hr.handler.builder.SpecificationsBuilder;
import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.domain.OfferSpecs;
import com.web.app.heaven_hr.offer.dto.OfferDto;
import com.web.app.heaven_hr.offer.exception.DuplicateOfferException;
import com.web.app.heaven_hr.offer.exception.OfferNotFoundException;
import com.web.app.heaven_hr.offer.service.OfferService;
import com.web.app.heaven_hr.offer.util.mapper.OfferDtoToOfferMapper;
import com.web.app.heaven_hr.offer.util.mapper.OfferToOfferDtoMapper;
import com.web.app.heaven_hr.util.constants.ApiPathConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote OfferController is used to expose endpoints for offer.
 * @since 2019
 */

@RestController
@RequestMapping(ApiPathConstants.OFFER)
public class OfferController {

    @Autowired
    private OfferService service;

    @Autowired
    private OfferDtoToOfferMapper offerDtoToOfferMapper;

    @Autowired
    private OfferToOfferDtoMapper offerToOfferDtoMapper;

    @Autowired
    private ApplicationToApplicationDtoMapper applicationToApplicationDtoMapper;

    /**
     * @param offerDto
     * @return
     * @throws DuplicateOfferException
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public OfferDto createOffer(@Valid @RequestBody OfferDto offerDto) throws DuplicateOfferException {
        Offer offer = this.offerDtoToOfferMapper.convert(offerDto);
        Offer createdOffer = this.service.create(offer);
        return this.offerToOfferDtoMapper.convert(createdOffer);
    }

    /**
     * @param offerId
     * @return
     * @throws OfferNotFoundException
     */
    @GetMapping(value = "/{offerId:[0-9]+}")
    @ResponseBody
    public OfferDto findOfferById(@Valid @PathVariable("offerId") Long offerId) throws OfferNotFoundException {
        Offer offer = this.service.queryById(offerId);
        return this.offerToOfferDtoMapper.convert(offer);
    }

    /**
     * @param page
     * @param pageSize
     * @param sortProperties
     * @param sortAscending
     * @param jobTitles
     * @param ids
     * @return
     * @throws IllegalArgumentException
     */
    @GetMapping(value = "")
    @ResponseBody
    public Iterable<OfferDto> listOffers(
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "page_size") Integer pageSize,
            @RequestParam(required = false, value = "sort_props[]") String[] sortProperties,
            @RequestParam(required = false, value = "sort_asc", defaultValue = "false") boolean sortAscending,
            @RequestParam(required = false, value = "job_titles[]") Set<String> jobTitles,
            @RequestParam(required = false, value = "ids[]") Set<Long> ids)
            throws IllegalArgumentException {
        SpecificationsBuilder<Offer> specificationsBuilder = new SpecificationsBuilder<>();
        specificationsBuilder.addSpecification(OfferSpecs.filterByIds(ids));
        specificationsBuilder.addSpecification(OfferSpecs.filterByJobTitles(jobTitles));

        if (Objects.nonNull(page) && page >= 0 && Objects.nonNull(pageSize) && pageSize > 0) {
            Sort sort = new Sort(sortAscending ? Sort.Direction.ASC : Sort.Direction.DESC, Optional.ofNullable(sortProperties)
                    .map(items -> Arrays.stream(items)
                            .filter(StringUtils::isNotBlank))
                    .orElse(Stream.of("startDate"))
                    .toArray(String[]::new));
            PageRequest pageRequest = new PageRequest(page, pageSize, sort);
            Page<Offer> offers = this.service.query(specificationsBuilder.build(), pageRequest);
            return offers.map(this.offerToOfferDtoMapper::convert);
        }
        Iterable<Offer> offers = this.service.query(specificationsBuilder.build());
        return StreamSupport.stream(offers.spliterator(), false)
                .map(this.offerToOfferDtoMapper::convert)
                .collect(Collectors.toSet());
    }

    /**
     * @param offerId
     * @return
     * @throws OfferNotFoundException
     */
    @GetMapping("/{offerId:[0-9]+}/applications")
    @ResponseBody
    public Iterable<ApplicationDto> getApplicationsLinkedToOfferId(@PathVariable("offerId") Long offerId) throws OfferNotFoundException {
        Set<Application> applications = this.service.findApplicationsLinkedToOffer(offerId);
        return applications.stream().filter(Objects::nonNull).map(application -> this.applicationToApplicationDtoMapper.convert(application)).collect(Collectors.toList());
    }

    /**
     * @param offerId
     * @param applicationId
     * @return
     * @throws OfferNotFoundException
     * @throws ApplicationNotFoundException
     */
    @GetMapping("/{offerId:[0-9]+}/applications/{applicationId:[0-9]+}")
    @ResponseBody
    public ApplicationDto getApplicationLinkedToOfferId(@PathVariable("offerId") Long offerId, @PathVariable("applicationId") Long applicationId) throws OfferNotFoundException, ApplicationNotFoundException {
        Application application = this.service.findApplicationLinkedToOffer(offerId, applicationId);
        return this.applicationToApplicationDtoMapper.convert(application);
    }
}