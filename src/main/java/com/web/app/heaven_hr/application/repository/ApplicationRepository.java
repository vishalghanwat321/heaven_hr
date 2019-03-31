package com.web.app.heaven_hr.application.repository;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.offer.domain.Offer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationRepository
 * @since 2019
 */
@Repository
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long>, JpaSpecificationExecutor, Serializable {
    boolean existsByEmailAndOffer(String email, Offer offer);
}
