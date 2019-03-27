package com.web.app.heaven_hr.util.unique_sequence_generator;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote AbstractRandomLongIdEntity is to generate random long id.
 * @since 2019
 */

@MappedSuperclass
public class AbstractRandomLongIdEntity implements Serializable {
    /**
     * The randomized ID should not exceed this value.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/MAX_SAFE_INTEGER">MAX_SAFE_INTEGER</a>
     */
    private static final Long MAX_SAFE_NUMBER = (long) Math.pow(2, 53) - 1;

    @Id
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false, updatable = false)
    protected Long id;

    @PrePersist
    protected void onPrePersist() {
        this.id = ThreadLocalRandom.current().nextLong(this.MAX_SAFE_NUMBER);
    }

    public Long getId() {
        return id;
    }
}