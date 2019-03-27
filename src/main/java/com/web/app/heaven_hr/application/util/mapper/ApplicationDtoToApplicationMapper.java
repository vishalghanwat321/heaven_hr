package com.web.app.heaven_hr.application.util.mapper;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.dto.ApplicationDto;
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
 * @implNote ApplicationDtoToApplicationMapper will map bean ApplicationDto to Application using model mapper
 * @since 2019
 */
@Component
public class ApplicationDtoToApplicationMapper {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationDtoToApplicationMapper.class);

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    protected void onPostConstruct() {
        this.initializeTypeMaps();
    }

    private TypeMap<ApplicationDto, Application> typeMapDTO;

    private void initializeTypeMaps() {
        logger.info("initializing mapper to map ApplicationDto to Application...");
        this.typeMapDTO = this.modelMapper.createTypeMap(ApplicationDto.class, Application.class)
                .addMapping(ApplicationDto::getCandidateEmail, Application::setEmail)
                .addMapping(ApplicationDto::getApplicationStatus, Application::setStatus)
                .addMapping(ApplicationDto::getResume, Application::setResume);
    }

    public Application convert(ApplicationDto dto) {
        if (Objects.isNull(dto))
            return null;
        return this.typeMapDTO.map(dto);
    }
}