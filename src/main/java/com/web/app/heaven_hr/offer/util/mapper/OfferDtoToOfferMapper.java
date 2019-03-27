package com.web.app.heaven_hr.offer.util.mapper;

import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.dto.OfferDto;
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
 * @implNote OfferDtoToOfferMapper will map bean OfferDto to Offer using model mapper
 * @since 2019
 */
@Component
public class OfferDtoToOfferMapper {

    private static final Logger logger = LoggerFactory.getLogger(OfferDtoToOfferMapper.class);

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    protected void onPostConstruct() {
        this.initializeTypeMaps();
    }

    private TypeMap<OfferDto, Offer> typeMapDTO;

    private void initializeTypeMaps() {
        logger.info("initializing mapper to map OfferDto to Offer...");
        this.typeMapDTO = this.modelMapper.createTypeMap(OfferDto.class, Offer.class)
                .addMapping(OfferDto::getJobTitle, Offer::setJobTitle);
    }

    public Offer convert(OfferDto dto) {
        if (Objects.isNull(dto))
            return null;
        return this.typeMapDTO.map(dto);
    }
}