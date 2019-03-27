package com.web.app.heaven_hr.offer.repository;

import com.web.app.heaven_hr.offer.domain.Offer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote OfferRepository
 * @since 2019
 */

@Repository
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long>, JpaSpecificationExecutor, Serializable {

    boolean existsByJobTitleIgnoreCase(String jobTitle);
}