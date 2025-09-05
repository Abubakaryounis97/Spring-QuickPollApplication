package io.zipcoder.tc_spring_poll_application.repositories;

import io.zipcoder.tc_spring_poll_application.domain.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
    
}
