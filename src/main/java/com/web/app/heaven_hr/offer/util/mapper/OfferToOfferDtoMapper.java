package com.web.app.heaven_hr.offer.util.mapper;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.dto.OfferDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote OfferToOfferDtoMapper will map bean Offer to OfferDto using model mapper
 * @since 2019
 */
@Component
public class OfferToOfferDtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(OfferToOfferDtoMapper.class);

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    protected void onPostConstruct() {
        this.initializeTypeMaps();
    }

    private TypeMap<Offer, OfferDto> typeMapDTO;

    private void initializeTypeMaps() {
        logger.info("initializing mapper to map Offer to OfferDto...");
        Converter<Set<Application>, Set<Long>> converterApplicationsToIDs = ctx -> Objects.isNull(ctx.getSource()) ?
                null :
                ctx.getSource().stream()
                        .filter(Objects::nonNull)
                        .map(dto -> dto.getId())
                        .collect(Collectors.toSet());
        this.typeMapDTO = this.modelMapper.createTypeMap(Offer.class, OfferDto.class)
                .addMapping(Offer::getJobTitle, OfferDto::setJobTitle)
                .addMapping(Offer::getReceivedApplications, OfferDto::setNumberOfApplications)
                .addMapping(Offer::getStartDate, OfferDto::setStartDate)
                .addMapping(Offer::getId, OfferDto::setId)
                .addMappings(mapper -> mapper.using(converterApplicationsToIDs).map(Offer::getApplications, OfferDto::setApplications));
    }

    public OfferDto convert(Offer entity) {
        if (Objects.isNull(entity))
            return null;
        return this.typeMapDTO.map(entity);
    }
}