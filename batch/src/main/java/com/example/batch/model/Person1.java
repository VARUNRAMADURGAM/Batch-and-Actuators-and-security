package com.example.batch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Person1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "person_id")
    @SequenceGenerator(name="person_id",sequenceName = "person_id_seq",initialValue = 1000,allocationSize = 10)
    private Long id;

    private String name;
    private String birthDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
