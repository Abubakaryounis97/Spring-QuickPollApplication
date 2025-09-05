package io.zipcoder.tc_spring_poll_application.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollRepository pollRepository;   // JPA creates implementation of this interface automatically

    @Autowired
    public PollController(PollRepository pollRepository) { // inject the repository into the controller
        this.pollRepository = pollRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Poll p = pollRepository.findOne(pollId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    // post
    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
    // Save the poll to the database
    poll = pollRepository.save(poll);

    // Build the URI for the new poll: /polls/{id}
    URI newPollUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(poll.getId())
            .toUri();

    // Set the Location header with the new poll URI
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(newPollUri);

    // Return 201 Created with Location header
    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
}

}
