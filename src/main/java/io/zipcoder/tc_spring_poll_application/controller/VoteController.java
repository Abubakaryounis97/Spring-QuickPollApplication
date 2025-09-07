package io.zipcoder.tc_spring_poll_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus; // @Autowired -> injects dependency automatically
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // @RestController, @RequestMapping
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;

@RestController // tells Spring this class is a REST controller
public class VoteController {

    private final VoteRepository voteRepository;

    @Autowired // Spring injects VoteRepository into the constructor
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST) // maps POST request
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        vote = voteRepository.save(vote); // save new vote
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}") // build URI for new vote
                        .buildAndExpand(vote.getId())
                        .toUri()
        );
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polls/votes", method = RequestMethod.GET) // maps GET request
    public Iterable<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.GET) // maps GET request
    public Iterable<Vote> getVotesByPoll(@PathVariable Long pollId) {
        return voteRepository.findVotesByPoll(pollId);
    }
}
