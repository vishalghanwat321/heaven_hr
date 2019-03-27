package com.web.app.heaven_hr.application.domain;

import com.web.app.heaven_hr.offer.domain.Offer;
import com.web.app.heaven_hr.offer.domain.Offer_;
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
public class ApplicationSpecs {

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

    private static Specification<Offer> emptyAnd() {
        return (root, query, cb) -> cb.and();
    }
}