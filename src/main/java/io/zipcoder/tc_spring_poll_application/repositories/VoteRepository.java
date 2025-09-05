package io.zipcoder.tc_spring_poll_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.zipcoder.tc_spring_poll_application.domain.Vote;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long> {
    
}
