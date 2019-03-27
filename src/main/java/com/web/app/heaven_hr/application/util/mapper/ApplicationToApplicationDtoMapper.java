package com.web.app.heaven_hr.application.util.mapper;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.dto.ApplicationDto;
import com.web.app.heaven_hr.offer.domain.Offer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationToApplicationDtoMapper will map bean Application to ApplicationDto using model mapper
 * @since 2019
 */

@Component
public class ApplicationToApplicationDtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationToApplicationDtoMapper.class);

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    protected void onPostConstruct() {
        this.initializeTypeMaps();
    }

    private TypeMap<Application, ApplicationDto> typeMapDTO;

    private void initializeTypeMaps() {
        logger.info("initializing mapper to map Application to ApplicationDto...");
        Converter<Offer, Long> convertOfferToId = ctx -> Objects.isNull(ctx.getSource()) ?
                null :
                ctx.getSource().getId();
        this.typeMapDTO = this.modelMapper.createTypeMap(Application.class, ApplicationDto.class)
                .addMapping(Application::getEmail, ApplicationDto::setCandidateEmail)
                .addMapping(Application::getStatus, ApplicationDto::setApplicationStatus)
                .addMapping(Application::getResume, ApplicationDto::setResume)
                .addMapping(Application::getId, ApplicationDto::setId)
                .addMappings(mapper -> mapper.using(convertOfferToId).map(Application::getOffer, ApplicationDto::setOfferId));
    }

    public ApplicationDto convert(Application entity) {
        if (Objects.isNull(entity))
            return null;
        return this.typeMapDTO.map(entity);
    }
}
