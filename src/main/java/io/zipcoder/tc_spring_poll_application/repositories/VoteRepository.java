package io.zipcoder.tc_spring_poll_application.repositories;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.tc_spring_poll_application.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    // No need to define any methods manually
    // Spring Data JPA automatically provides save, findById, findAll, deleteById, etc.
}
