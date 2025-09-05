package io.zipcoder.tc_spring_poll_application.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="POLL_ID")
    
    private Long id;

    @Column(name="QUESTION")
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "POLL_ID")
    @OrderBy
    private Set<Option> options = new HashSet<>();

    public Poll() {

    }

    // Getters and Setters
    public Long getId(){
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

}
