package com.web.app.heaven_hr.offer.domain;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @since 2019
 */

public class OfferSpecs {

    public static Specification<Offer> filterByIds(Iterable<Long> filterIds) {
        return Optional.ofNullable(filterIds)
                .map(items -> StreamSupport.stream(items.spliterator(), false)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .map(validIds -> (Specification<Offer>) (root, query, cb) -> {
                    query.distinct(true);
                    return root.get(Offer_.id).in(validIds);
                })
                .orElse(emptyAnd());
    }

    public static Specification<Offer> filterByJobTitles(Iterable<String> filterJobTitles) {
        return Optional.ofNullable(filterJobTitles)
                .map(items -> StreamSupport.stream(items.spliterator(), false)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toSet()))
                .map(validJobTitles -> (Specification<Offer>) (root, query, cb) -> {
                    query.distinct(true);
                    return root.get(Offer_.jobTitle).in(validJobTitles);
                })
                .orElse(emptyAnd());
    }

    private static Specification<Offer> emptyAnd() {
        return (root, query, cb) -> cb.and();
    }
}