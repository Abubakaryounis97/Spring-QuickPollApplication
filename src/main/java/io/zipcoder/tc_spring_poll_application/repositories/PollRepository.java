package io.zipcoder.tc_spring_poll_application.repositories;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.tc_spring_poll_application.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {
    // No need to declare findById or deleteById, CrudRepository provides them
}
