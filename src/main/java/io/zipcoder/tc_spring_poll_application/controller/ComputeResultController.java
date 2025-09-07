package io.zipcoder.tc_spring_poll_application.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // @Autowired -> DI
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; // @RestController, @RequestMapping, @RequestParam
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.zipcoder.tc_spring_poll_application.domain.Option;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.dtos.OptionCount;
import io.zipcoder.tc_spring_poll_application.dtos.VoteResult;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;

@RestController // REST controller returning JSON
public class ComputeResultController {

    private final VoteRepository voteRepository;

    @Autowired // inject VoteRepository
    public ComputeResultController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET) // maps GET /computeresult?pollId=#
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        Iterable<Vote> allVotes = voteRepository.findVotesByPoll(pollId);

        Map<Long, Integer> counts = new LinkedHashMap<>();
        int total = 0;

        for (Vote v : allVotes) {
            Option o = v.getOption();
            if (o == null || o.getId() == null) continue;
            counts.merge(o.getId(), 1, Integer::sum);
            total++;
        }

        List<OptionCount> resultList = new ArrayList<>();
        for (Map.Entry<Long, Integer> e : counts.entrySet()) {
            OptionCount oc = new OptionCount();
            oc.setOptionId(e.getKey());
            oc.setCount(e.getValue());
            resultList.add(oc);
        }

        VoteResult voteResult = new VoteResult();
        voteResult.setTotalVotes(total);
        voteResult.setResults(resultList);

        return new ResponseEntity<>(voteResult, HttpStatus.OK);
    }
}
