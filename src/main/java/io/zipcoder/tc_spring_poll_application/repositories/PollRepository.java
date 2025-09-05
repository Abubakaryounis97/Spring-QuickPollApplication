package io.zipcoder.tc_spring_poll_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.zipcoder.tc_spring_poll_application.domain.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    public Object findById(Long pollId);
    
}
