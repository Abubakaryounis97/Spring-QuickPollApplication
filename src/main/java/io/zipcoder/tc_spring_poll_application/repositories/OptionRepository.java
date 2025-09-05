package io.zipcoder.tc_spring_poll_application.repositories;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.tc_spring_poll_application.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long> {
    // No need to define any methods manually
    // You can add custom queries later if needed
}
