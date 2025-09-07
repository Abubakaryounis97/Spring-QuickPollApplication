package io.zipcoder.tc_spring_poll_application.domain;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;                 // @Column -> map to specific column name
import jakarta.persistence.Entity;                // @Entity -> JPA entity
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;                    // @Id -> primary key
import jakarta.persistence.JoinColumn;            // @JoinColumn -> FK in child table
import jakarta.persistence.OneToMany;             // @OneToMany -> parent->children
import jakarta.persistence.OrderBy;               // @OrderBy -> stable ordering
import jakarta.validation.constraints.NotEmpty;   // validation (Boot 3 -> jakarta.*)
import jakarta.validation.constraints.Size;       // validation (min/max size)

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLL_ID")                     // match lab column name
    private Long id;

    @NotEmpty(message = "{NotEmpty.poll.question}") // Part 5 validation
    @Column(name = "QUESTION")                   // match lab column name
    private String question;

    @Size(min = 2, max = 6, message = "{Size.poll.options}") // Part 5 validation
    @OneToMany(cascade = CascadeType.ALL)        // unidirectional; options saved with poll
    @JoinColumn(name = "POLL_ID")                // FK stored in OPTION table
    @OrderBy
    private Set<Option> options;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Set<Option> getOptions() { return options; }
    public void setOptions(Set<Option> options) { this.options = options; }
}
