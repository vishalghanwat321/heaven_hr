package com.web.app.heaven_hr.application.domain;

import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;
import com.web.app.heaven_hr.util.unique_sequence_generator.AbstractRandomLongIdEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @since 2019
 */
@StaticMetamodel(Application.class)
public class Application_ extends AbstractRandomLongIdEntity_ {

    public static volatile SingularAttribute<Application, String> email;
    public static volatile SingularAttribute<Application, ApplicationStatus> status;
}
