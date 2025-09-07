package io.zipcoder.tc_spring_poll_application.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.exception.ResourceNotFoundException;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;

import jakarta.validation.Valid; // Spring Boot 3 uses jakarta.*

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollRepository pollRepository;

    @Autowired
    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    // GET /polls
    @GetMapping
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
    }

    // GET /polls/{pollId}
    @GetMapping("/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        return new ResponseEntity<>(pollRepository.findById(pollId).get(), HttpStatus.OK);
    }

    // POST /polls
    @PostMapping
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // PUT /polls/{pollId}
    @PutMapping("/{pollId}")
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        poll.setId(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE /polls/{pollId}
    @DeleteMapping("/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // helper method: throw 404 if poll missing
    protected void verifyPoll(Long pollId) {
        if (!pollRepository.findById(pollId).isPresent()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
    }
}
