package com.web.app.heaven_hr.offer.domain;

import com.web.app.heaven_hr.util.unique_sequence_generator.AbstractRandomLongIdEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @since 2019
 */
@StaticMetamodel(Offer.class)
public class Offer_ extends AbstractRandomLongIdEntity_ {

    public static volatile SingularAttribute<Offer, String> jobTitle;
}
